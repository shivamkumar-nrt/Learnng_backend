package com.example.demo.modules.adminusers;

import com.example.demo.common.api.ApiResponse;
import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.api.PageResponse;
import com.example.demo.common.api.PageResponseMapper;
import com.example.demo.modules.rbac.AuditContextFactory;
import com.example.demo.modules.adminusers.dto.AdminUserRequest;
import com.example.demo.modules.adminusers.dto.AdminUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public ApiResponse<PageResponse<AdminUserResponse>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) Boolean active,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        return ApiResponse.ok(PageResponseMapper.from(
                adminUserService.search(q, role, companyName, active, params),
                AdminUserMapper::toResponse
        ));
    }

    @PostMapping
    public ApiResponse<AdminUserResponse> create(@Valid @RequestBody AdminUserRequest request, HttpServletRequest httpRequest) {
        return ApiResponse.ok(AdminUserMapper.toResponse(adminUserService.create(request, AuditContextFactory.from(httpRequest))), "Admin user created");
    }

    @PatchMapping("/{id}/active")
    public ApiResponse<AdminUserResponse> toggleActive(@PathVariable Long id, @RequestParam boolean active, HttpServletRequest httpRequest) {
        return ApiResponse.ok(AdminUserMapper.toResponse(adminUserService.toggleActive(id, active, AuditContextFactory.from(httpRequest))), "Admin user status updated");
    }
}
