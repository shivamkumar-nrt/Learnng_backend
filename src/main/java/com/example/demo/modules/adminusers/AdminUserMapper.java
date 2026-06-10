package com.example.demo.modules.adminusers;

import com.example.demo.modules.adminusers.dto.AdminUserResponse;
import com.example.demo.modules.auth.AuthUser;
import com.example.demo.modules.auth.Permission;
import com.example.demo.modules.auth.Role;
import java.util.stream.Collectors;

public final class AdminUserMapper {

    private AdminUserMapper() {
    }

    public static AdminUserResponse toResponse(AuthUser entity) {
        return new AdminUserResponse(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getCompanyName(),
                entity.getRole(),
                entity.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()),
                entity.getTier(),
                entity.isActive(),
                entity.getPermissions().stream().map(Permission::getCode).collect(Collectors.toSet())
        );
    }
}
