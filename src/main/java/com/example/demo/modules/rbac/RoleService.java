package com.example.demo.modules.rbac;

import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.exception.NotFoundException;
import com.example.demo.common.persistence.SpecificationUtils;
import com.example.demo.modules.audit.AuditActor;
import com.example.demo.modules.audit.AuditLogService;
import com.example.demo.modules.auth.Permission;
import com.example.demo.modules.auth.PermissionRepository;
import com.example.demo.modules.auth.Role;
import com.example.demo.modules.auth.RoleRepository;
import com.example.demo.modules.rbac.dto.RoleRequest;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final AuditLogService auditLogService;

    public RoleService(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            AuditLogService auditLogService
    ) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.auditLogService = auditLogService;
    }

    @Transactional(readOnly = true)
    public Page<Role> search(String q, Boolean systemRole, PageRequestParams params) {
        Specification<Role> specification = Specification
                .where(SpecificationUtils.<Role>multiFieldContains(q, "code", "name", "description"))
                .and((root, query, builder) -> systemRole == null ? builder.conjunction() : builder.equal(root.get("systemRole"), systemRole));

        Pageable pageable = PageRequest.of(
                params.safePage(),
                params.safeSize(),
                Sort.by(
                        "asc".equalsIgnoreCase(params.safeSortDir()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                        params.sortBy() == null || params.sortBy().isBlank() ? "createdAt" : params.sortBy()
                )
        );

        return roleRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Set<Permission> getAllPermissions() {
        return new HashSet<>(permissionRepository.findAll());
    }

    @Transactional
    public Role create(RoleRequest request, AuditActor actor) {
        if (roleRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Role code already exists");
        }
        Role role = new Role();
        apply(role, request);
        Role saved = roleRepository.save(role);
        auditLogService.log(actor, "ROLE_CREATE", "Role", saved.getCode(), null, saved.getDescription(), "Created new RBAC role");
        return saved;
    }

    @Transactional
    public Role update(Long id, RoleRequest request, AuditActor actor) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
        String oldValue = role.getName() + " | " + role.getDescription();
        apply(role, request);
        Role saved = roleRepository.save(role);
        auditLogService.log(actor, "ROLE_EDIT", "Role", saved.getCode(), oldValue, saved.getName() + " | " + saved.getDescription(), "Updated RBAC role");
        return saved;
    }

    @Transactional
    public void delete(Long id, AuditActor actor) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
        if (role.isSystemRole()) {
            throw new IllegalArgumentException("System roles cannot be deleted");
        }
        auditLogService.log(actor, "ROLE_DELETE", "Role", role.getCode(), role.getDescription(), null, "Deleted custom RBAC role");
        roleRepository.delete(role);
    }

    private void apply(Role role, RoleRequest request) {
        role.setCode(request.code().trim().toUpperCase());
        role.setName(request.name());
        role.setDescription(request.description());
        role.setSystemRole(request.systemRole() != null && request.systemRole());
        role.setPermissions(resolvePermissions(request.permissions()));
    }

    private Set<Permission> resolvePermissions(Set<String> codes) {
        if (codes == null || codes.isEmpty()) {
            throw new IllegalArgumentException("At least one permission is required");
        }
        Set<Permission> permissions = new HashSet<>();
        for (String code : codes) {
            Permission permission = permissionRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown permission: " + code));
            permissions.add(permission);
        }
        return permissions;
    }
}
