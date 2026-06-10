package com.example.demo.modules.notifications;

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
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationItemRepository notificationItemRepository;

    public NotificationController(NotificationItemRepository notificationItemRepository) {
        this.notificationItemRepository = notificationItemRepository;
    }

    @GetMapping
    public ApiResponse<PageResponse<NotificationItem>> search(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String channel,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        Specification<NotificationItem> specification = Specification
                .where(SpecificationUtils.<NotificationItem>equalsIgnoreCase("category", category))
                .and(SpecificationUtils.<NotificationItem>equalsIgnoreCase("channel", channel));

        return ApiResponse.ok(PageResponseMapper.from(
                notificationItemRepository.findAll(specification,
                        PageRequest.of(params.safePage(), params.safeSize(), Sort.by(Sort.Direction.DESC, "createdAt"))),
                item -> item
        ));
    }
}
