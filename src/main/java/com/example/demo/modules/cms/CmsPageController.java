package com.example.demo.modules.cms;

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
@RequestMapping("/api/v1/cms/pages")
public class CmsPageController {

    private final CmsPageRepository cmsPageRepository;

    public CmsPageController(CmsPageRepository cmsPageRepository) {
        this.cmsPageRepository = cmsPageRepository;
    }

    @GetMapping
    public ApiResponse<PageResponse<CmsPage>> search(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String q,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        Specification<CmsPage> specification = Specification
                .where(SpecificationUtils.<CmsPage>equalsIgnoreCase("status", status))
                .and(SpecificationUtils.<CmsPage>multiFieldContains(q, "title", "slug", "excerpt"));

        return ApiResponse.ok(PageResponseMapper.from(
                cmsPageRepository.findAll(specification,
                        PageRequest.of(params.safePage(), params.safeSize(), Sort.by(Sort.Direction.DESC, "createdAt"))),
                item -> item
        ));
    }
}
