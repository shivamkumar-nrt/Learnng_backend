package com.example.demo.modules.rbac;

import com.example.demo.modules.auth.Permission;
import com.example.demo.modules.auth.Role;
import com.example.demo.modules.rbac.dto.RoleResponse;
import java.util.stream.Collectors;

public final class RoleMapper {

    private RoleMapper() {
    }

    public static RoleResponse toResponse(Role entity) {
        return new RoleResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.isSystemRole(),
                entity.getPermissions().stream().map(Permission::getCode).collect(Collectors.toSet())
        );
    }
}
