package com.example.demo.modules.wallet;

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
@RequestMapping("/api/v1/wallet/transactions")
public class WalletController {

    private final WalletTransactionRepository walletTransactionRepository;

    public WalletController(WalletTransactionRepository walletTransactionRepository) {
        this.walletTransactionRepository = walletTransactionRepository;
    }

    @GetMapping
    public ApiResponse<PageResponse<WalletTransaction>> search(
            @RequestParam(required = false) String userEmail,
            @RequestParam(required = false) String walletType,
            @Valid @ModelAttribute PageRequestParams params
    ) {
        Specification<WalletTransaction> specification = Specification
                .where(SpecificationUtils.<WalletTransaction>containsIgnoreCase("userEmail", userEmail))
                .and(SpecificationUtils.<WalletTransaction>equalsIgnoreCase("walletType", walletType));

        return ApiResponse.ok(PageResponseMapper.from(
                walletTransactionRepository.findAll(specification,
                        PageRequest.of(params.safePage(), params.safeSize(), Sort.by(Sort.Direction.DESC, "createdAt"))),
                item -> item
        ));
    }
}
