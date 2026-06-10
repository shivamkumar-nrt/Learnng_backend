package com.example.demo.modules.auth;

import com.example.demo.common.security.JwtService;
import com.example.demo.modules.auth.dto.AuthResponse;
import com.example.demo.modules.auth.dto.LoginRequest;
import com.example.demo.modules.auth.dto.RegisterRequest;
import com.example.demo.modules.members.MemberProfile;
import com.example.demo.modules.members.MemberProfileRepository;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PermissionRepository permissionRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AuthUserRepository authUserRepository,
            PermissionRepository permissionRepository,
            MemberProfileRepository memberProfileRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authUserRepository = authUserRepository;
        this.permissionRepository = permissionRepository;
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
        authUser.setRole("MEMBER");
        authUser.setTier(request.tier() == null || request.tier().isBlank() ? "FREE" : request.tier());
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
        return new AuthResponse(token, authUser.getEmail(), authUser.getFullName(), authUser.getRole(), authUser.getTier(), authUser.getPermissions().stream().map(Permission::getCode).collect(Collectors.toSet()));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        AuthUser authUser = authUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), authUser.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(authUser.getEmail(), authUser.getRole());
        return new AuthResponse(token, authUser.getEmail(), authUser.getFullName(), authUser.getRole(), authUser.getTier(), authUser.getPermissions().stream().map(Permission::getCode).collect(Collectors.toSet()));
    }
}
