package com.example.demo.modules.members;

import com.example.demo.common.api.ApiResponse;
import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.api.PageResponse;
import com.example.demo.common.api.PageResponseMapper;
import com.example.demo.modules.members.dto.MemberProfileRequest;
import com.example.demo.modules.members.dto.MemberProfileResponse;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/members")
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    public MemberProfileController(MemberProfileService memberProfileService) {
        this.memberProfileService = memberProfileService;
    }

    @GetMapping
    public ApiResponse<PageResponse<MemberProfileResponse>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String religion,
            @RequestParam(required = false) Boolean verified,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        return ApiResponse.ok(PageResponseMapper.from(
                memberProfileService.search(q, city, religion, verified, params),
                MemberProfileMapper::toResponse
        ));
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberProfileResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(MemberProfileMapper.toResponse(memberProfileService.getById(id)));
    }

    @PostMapping
    public ApiResponse<MemberProfileResponse> create(@Valid @RequestBody MemberProfileRequest request) {
        return ApiResponse.ok(MemberProfileMapper.toResponse(memberProfileService.create(request)), "Member profile created");
    }

    @PutMapping("/{id}")
    public ApiResponse<MemberProfileResponse> update(@PathVariable Long id, @Valid @RequestBody MemberProfileRequest request) {
        return ApiResponse.ok(MemberProfileMapper.toResponse(memberProfileService.update(id, request)), "Member profile updated");
    }
}
