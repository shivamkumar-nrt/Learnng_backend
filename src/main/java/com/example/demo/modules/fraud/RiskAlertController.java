package com.example.demo.modules.fraud;

import com.example.demo.common.api.ApiResponse;
import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.api.PageResponse;
import com.example.demo.common.api.PageResponseMapper;
import com.example.demo.common.persistence.SpecificationUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fraud/alerts")
public class RiskAlertController {

    private final RiskAlertRepository riskAlertRepository;

    public RiskAlertController(RiskAlertRepository riskAlertRepository) {
        this.riskAlertRepository = riskAlertRepository;
    }

    @GetMapping
    public ApiResponse<PageResponse<RiskAlert>> search(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String status,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        Specification<RiskAlert> specification = Specification
                .where(SpecificationUtils.<RiskAlert>equalsIgnoreCase("severity", severity))
                .and(SpecificationUtils.<RiskAlert>equalsIgnoreCase("status", status));

        return ApiResponse.ok(PageResponseMapper.from(
                riskAlertRepository.findAll(specification,
                        PageRequest.of(params.safePage(), params.safeSize(), Sort.by(Sort.Direction.DESC, "createdAt"))),
                item -> item
        ));
    }
}
