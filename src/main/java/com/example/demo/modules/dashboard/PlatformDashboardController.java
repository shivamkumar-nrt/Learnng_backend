package com.example.demo.modules.dashboard;

import com.example.demo.common.api.ApiResponse;
import com.example.demo.modules.crm.CrmLeadRepository;
import com.example.demo.modules.fraud.RiskAlert;
import com.example.demo.modules.fraud.RiskAlertRepository;
import com.example.demo.modules.members.MemberProfileRepository;
import com.example.demo.modules.stories.SuccessStoryRepository;
import com.example.demo.modules.wallet.WalletTransactionRepository;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/platform/dashboard")
public class PlatformDashboardController {

    private final MemberProfileRepository memberProfileRepository;
    private final CrmLeadRepository crmLeadRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final RiskAlertRepository riskAlertRepository;
    private final SuccessStoryRepository successStoryRepository;

    public PlatformDashboardController(
            MemberProfileRepository memberProfileRepository,
            CrmLeadRepository crmLeadRepository,
            WalletTransactionRepository walletTransactionRepository,
            RiskAlertRepository riskAlertRepository,
            SuccessStoryRepository successStoryRepository
    ) {
        this.memberProfileRepository = memberProfileRepository;
        this.crmLeadRepository = crmLeadRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.riskAlertRepository = riskAlertRepository;
        this.successStoryRepository = successStoryRepository;
    }

    @GetMapping
    public ApiResponse<PlatformDashboardResponse> get() {
        Map<String, Long> totals = Map.of(
                "members", memberProfileRepository.count(),
                "leads", crmLeadRepository.count(),
                "walletTransactions", walletTransactionRepository.count(),
                "riskAlerts", riskAlertRepository.count(),
                "successStories", successStoryRepository.count()
        );

        Map<String, Long> crmByStatus = crmLeadRepository.findAll().stream()
                .collect(Collectors.groupingBy(lead -> lead.getStatus().name(), Collectors.counting()));

        Map<String, Long> riskBySeverity = riskAlertRepository.findAll().stream()
                .collect(Collectors.groupingBy(RiskAlert::getSeverity, Collectors.counting()));

        return ApiResponse.ok(new PlatformDashboardResponse(totals, crmByStatus, riskBySeverity));
    }
}
