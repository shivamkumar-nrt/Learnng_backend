package com.example.demo.modules.rbac;

import com.example.demo.common.api.ApiResponse;
import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.api.PageResponse;
import com.example.demo.common.api.PageResponseMapper;
import com.example.demo.modules.auth.Permission;
import com.example.demo.modules.rbac.dto.RoleRequest;
import com.example.demo.modules.rbac.dto.RoleResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ApiResponse<PageResponse<RoleResponse>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean systemRole,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        return ApiResponse.ok(PageResponseMapper.from(roleService.search(q, systemRole, params), RoleMapper::toResponse));
    }

    @GetMapping("/permissions")
    public ApiResponse<Set<String>> permissions() {
        return ApiResponse.ok(roleService.getAllPermissions().stream().map(Permission::getCode).collect(Collectors.toSet()));
    }

    @PostMapping
    public ApiResponse<RoleResponse> create(@Valid @RequestBody RoleRequest request, HttpServletRequest httpRequest) {
        return ApiResponse.ok(RoleMapper.toResponse(roleService.create(request, AuditContextFactory.from(httpRequest))), "Role created");
    }

    @PutMapping("/{id}")
    public ApiResponse<RoleResponse> update(@PathVariable Long id, @Valid @RequestBody RoleRequest request, HttpServletRequest httpRequest) {
        return ApiResponse.ok(RoleMapper.toResponse(roleService.update(id, request, AuditContextFactory.from(httpRequest))), "Role updated");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        roleService.delete(id, AuditContextFactory.from(httpRequest));
        return new ApiResponse<>(true, null, "Role deleted");
    }
}
