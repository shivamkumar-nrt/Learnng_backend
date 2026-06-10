package com.example.demo.modules.adminusers;

import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.exception.NotFoundException;
import com.example.demo.common.persistence.SpecificationUtils;
import com.example.demo.modules.audit.AuditActor;
import com.example.demo.modules.audit.AuditLogService;
import com.example.demo.modules.adminusers.dto.AdminUserRequest;
import com.example.demo.modules.auth.AuthUser;
import com.example.demo.modules.auth.AuthUserRepository;
import com.example.demo.modules.auth.Permission;
import com.example.demo.modules.auth.PermissionRepository;
import com.example.demo.modules.auth.Role;
import com.example.demo.modules.auth.RoleRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserService {

    private static final Set<String> MEMBER_ROLE_CODES = Set.of("MEMBER", "USER", "PREMIUM_USER", "ELITE_USER");

    private final AuthUserRepository authUserRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;

    public AdminUserService(
            AuthUserRepository authUserRepository,
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuditLogService auditLogService
    ) {
        this.authUserRepository = authUserRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
    }

    @Transactional(readOnly = true)
    public Page<AuthUser> search(String q, String role, String companyName, Boolean active, PageRequestParams params) {
        Specification<AuthUser> specification = Specification
                .where(SpecificationUtils.<AuthUser>multiFieldContains(q, "fullName", "email", "phone", "companyName"))
                .and(SpecificationUtils.<AuthUser>equalsIgnoreCase("role", role))
                .and(SpecificationUtils.<AuthUser>containsIgnoreCase("companyName", companyName))
                .and((root, query, builder) -> active == null ? builder.conjunction() : builder.equal(root.get("active"), active))
                .and((root, query, builder) -> builder.not(root.get("role").in(MEMBER_ROLE_CODES)));

        Pageable pageable = PageRequest.of(
                params.safePage(),
                params.safeSize(),
                Sort.by(
                        "asc".equalsIgnoreCase(params.safeSortDir()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                        params.sortBy() == null || params.sortBy().isBlank() ? "createdAt" : params.sortBy()
                )
        );

        return authUserRepository.findAll(specification, pageable);
    }

    @Transactional
    public AuthUser create(AdminUserRequest request, AuditActor actor) {
        if (authUserRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (authUserRepository.existsByPhone(request.phone())) {
            throw new IllegalArgumentException("Phone already registered");
        }

        AuthUser entity = new AuthUser();
        entity.setFullName(request.fullName());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setCompanyName(request.companyName());
        entity.setPasswordHash(passwordEncoder.encode(request.password()));
        entity.setRole(normalizeRole(request.roleCode()));
        entity.setTier(request.tier() == null || request.tier().isBlank() ? "ENTERPRISE" : request.tier());
        entity.setActive(request.active() == null || request.active());
        entity.setPermissions(resolvePermissions(request.permissions()));
        entity.setRoles(resolveRoles(request.roleCode()));
        AuthUser saved = authUserRepository.save(entity);
        auditLogService.log(actor, "USER_CREATE", "AdminUser", saved.getEmail(), null, saved.getRole() + " | " + saved.getCompanyName(), "Created admin user");
        return saved;
    }

    @Transactional
    public AuthUser toggleActive(Long id, boolean active, AuditActor actor) {
        AuthUser entity = authUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin user not found"));
        boolean oldActive = entity.isActive();
        entity.setActive(active);
        AuthUser saved = authUserRepository.save(entity);
        auditLogService.log(actor, active ? "USER_ACTIVATE" : "USER_SUSPEND", "AdminUser", saved.getEmail(), String.valueOf(oldActive), String.valueOf(active), "Toggled admin active status");
        return saved;
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return "ADMIN";
        }
        return role.trim().toUpperCase();
    }

    private Set<Role> resolveRoles(String roleCode) {
        String normalized = normalizeRole(roleCode);
        Role role = roleRepository.findByCode(normalized)
                .orElseThrow(() -> new IllegalArgumentException("Unknown role: " + normalized));
        return Set.of(role);
    }

    private Set<Permission> resolvePermissions(Set<String> codes) {
        Set<String> requested = (codes == null || codes.isEmpty())
                ? Set.of("CRM_MANAGE", "MEMBER_VIEW", "NOTIFICATION_MANAGE", "CMS_MANAGE", "FRAUD_MANAGE")
                : codes;

        Set<Permission> resolved = new HashSet<>();
        for (String code : requested) {
            Permission permission = permissionRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown permission: " + code));
            resolved.add(permission);
        }
        return resolved;
    }
}
