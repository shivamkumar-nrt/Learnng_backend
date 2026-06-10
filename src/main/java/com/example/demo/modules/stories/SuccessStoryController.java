package com.example.demo.modules.stories;

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
@RequestMapping("/api/v1/stories")
public class SuccessStoryController {

    private final SuccessStoryRepository successStoryRepository;

    public SuccessStoryController(SuccessStoryRepository successStoryRepository) {
        this.successStoryRepository = successStoryRepository;
    }

    @GetMapping
    public ApiResponse<PageResponse<SuccessStoryEntity>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean featured,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        Specification<SuccessStoryEntity> specification = Specification
                .where(SpecificationUtils.<SuccessStoryEntity>equalsIgnoreCase("city", city))
                .and((root, query, builder) -> featured == null ? builder.conjunction() : builder.equal(root.get("featured"), featured));

        return ApiResponse.ok(PageResponseMapper.from(
                successStoryRepository.findAll(specification,
                        PageRequest.of(params.safePage(), params.safeSize(), Sort.by(Sort.Direction.DESC, "createdAt"))),
                item -> item
        ));
    }
}
