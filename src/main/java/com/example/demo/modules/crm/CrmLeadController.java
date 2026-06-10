package com.example.demo.modules.crm;

import com.example.demo.common.api.ApiResponse;
import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.api.PageResponse;
import com.example.demo.common.api.PageResponseMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/crm/leads")
public class CrmLeadController {

    private final CrmLeadService crmLeadService;

    public CrmLeadController(CrmLeadService crmLeadService) {
        this.crmLeadService = crmLeadService;
    }

    @GetMapping
    public ApiResponse<PageResponse<CrmLeadResponse>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String rm,
            @RequestParam(required = false) String status,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        return ApiResponse.ok(PageResponseMapper.from(
                crmLeadService.search(q, rm, status, params),
                lead -> new CrmLeadResponse(
                        lead.getId(),
                        lead.getMemberName(),
                        lead.getEmail(),
                        lead.getCity(),
                        lead.getStatus(),
                        lead.getRelationshipManager(),
                        lead.getPriorityTier(),
                        lead.getSourceChannel(),
                        lead.getNextAction()
                )
        ));
    }
}
