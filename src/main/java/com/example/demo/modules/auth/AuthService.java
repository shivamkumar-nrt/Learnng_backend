package com.example.demo.modules.auth;

import com.example.demo.common.security.JwtService;
import com.example.demo.modules.auth.dto.AuthResponse;
import com.example.demo.modules.auth.dto.LoginRequest;
import com.example.demo.modules.auth.dto.RegisterRequest;
import com.example.demo.modules.members.MemberProfile;
import com.example.demo.modules.members.MemberProfileRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AuthUserRepository authUserRepository,
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            MemberProfileRepository memberProfileRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authUserRepository = authUserRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.memberProfileRepository = memberProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (authUserRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (authUserRepository.existsByPhone(request.phone())) {
            throw new IllegalArgumentException("Phone already registered");
        }

        AuthUser authUser = new AuthUser();
        authUser.setFullName(request.fullName());
        authUser.setEmail(request.email());
        authUser.setPhone(request.phone());
        authUser.setPasswordHash(passwordEncoder.encode(request.password()));
        authUser.setCompanyName("Katyyani Member Network");
        authUser.setRole("MEMBER");
        authUser.setTier(request.tier() == null || request.tier().isBlank() ? "FREE" : request.tier());
        authUser.setActive(true);
        authUser.setRoles(resolveDefaultRoles(authUser.getRole(), authUser.getTier()));
        authUserRepository.save(authUser);

        MemberProfile profile = new MemberProfile();
        profile.setMemberCode("MEM-" + authUser.getId());
        profile.setFullName(request.fullName());
        profile.setEmail(request.email());
        profile.setPhone(request.phone());
        profile.setReligion("Hindu");
        profile.setCommunity("General");
        profile.setCity("Bengaluru");
        profile.setState("Karnataka");
        profile.setProfession("New Member");
        profile.setEducation("Not updated");
        profile.setAnnualIncome("0-5 LPA");
        profile.setIntent("Serious");
        profile.setVerified(false);
        profile.setProfileCompletion(35);
        profile.setActive(true);
        profile.setBio("Newly registered member profile.");
        memberProfileRepository.save(profile);

        String token = jwtService.generateToken(authUser.getEmail(), authUser.getRole());
        return new AuthResponse(
                token,
                authUser.getEmail(),
                authUser.getFullName(),
                authUser.getRole(),
                authUser.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()),
                authUser.getTier(),
                authUser.getPermissions().stream().map(Permission::getCode).collect(Collectors.toSet())
        );
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        AuthUser authUser = authUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), authUser.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        if (!authUser.isActive()) {
            throw new IllegalArgumentException("Account is inactive");
        }

        String token = jwtService.generateToken(authUser.getEmail(), authUser.getRole());
        return new AuthResponse(
                token,
                authUser.getEmail(),
                authUser.getFullName(),
                authUser.getRole(),
                authUser.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()),
                authUser.getTier(),
                authUser.getPermissions().stream().map(Permission::getCode).collect(Collectors.toSet())
        );
    }

    private Set<Role> resolveDefaultRoles(String roleCode, String tier) {
        Set<Role> roles = authUserRoles(roleCode, tier);
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("Default roles not found");
        }
        return roles;
    }

    private Set<Role> authUserRoles(String roleCode, String tier) {
        Set<String> roleCodes = new java.util.LinkedHashSet<>();
        roleCodes.add(roleCode);
        if ("PREMIUM".equalsIgnoreCase(tier)) {
            roleCodes.add("PREMIUM_USER");
        }
        if ("ELITE".equalsIgnoreCase(tier)) {
            roleCodes.add("ELITE_USER");
        }

        return roleCodes.stream()
                .map(String::toUpperCase)
                .map(code -> roleRepository.findByCode(code)
                        .orElseThrow(() -> new IllegalArgumentException("Default role not found: " + code)))
                .collect(Collectors.toSet());
    }
}
