package com.example.demo.api;

import com.example.demo.model.EnterpriseModulesPayload.AdvertisementModule;
import com.example.demo.model.EnterpriseModulesPayload.AdminPanelConfig;
import com.example.demo.model.EnterpriseModulesPayload.ArchitectureOverview;
import com.example.demo.model.EnterpriseModulesPayload.CmsModule;
import com.example.demo.model.EnterpriseModulesPayload.CrmModule;
import com.example.demo.model.EnterpriseModulesPayload.FraudRiskModule;
import com.example.demo.model.EnterpriseModulesPayload.LocalizationModule;
import com.example.demo.model.EnterpriseModulesPayload.NotificationModule;
import com.example.demo.model.EnterpriseModulesPayload.PlatformOverview;
import com.example.demo.model.EnterpriseModulesPayload.SeoModule;
import com.example.demo.model.EnterpriseModulesPayload.SuccessStoryModule;
import com.example.demo.model.EnterpriseModulesPayload.WhatsAppModule;
import com.example.demo.model.EnterpriseModulesPayload.WalletModule;
import com.example.demo.service.EnterpriseModulesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseModulesController {

    private final EnterpriseModulesService enterpriseModulesService;

    public EnterpriseModulesController(EnterpriseModulesService enterpriseModulesService) {
        this.enterpriseModulesService = enterpriseModulesService;
    }

    @GetMapping("/overview")
    public PlatformOverview overview() {
        return enterpriseModulesService.getPlatformOverview();
    }

    @GetMapping("/admin-panel")
    public AdminPanelConfig adminPanel() {
        return enterpriseModulesService.getAdminPanelConfig();
    }

    @GetMapping("/architecture")
    public ArchitectureOverview architecture() {
        return enterpriseModulesService.getArchitectureOverview();
    }

    @GetMapping("/crm")
    public CrmModule crm() {
        return enterpriseModulesService.getCrmModule();
    }

    @GetMapping("/notifications")
    public NotificationModule notifications() {
        return enterpriseModulesService.getNotificationModule();
    }

    @GetMapping("/wallet")
    public WalletModule wallet() {
        return enterpriseModulesService.getWalletModule();
    }

    @GetMapping("/advertisements")
    public AdvertisementModule advertisements() {
        return enterpriseModulesService.getAdvertisementModule();
    }

    @GetMapping("/fraud-risk")
    public FraudRiskModule fraudRisk() {
        return enterpriseModulesService.getFraudRiskModule();
    }

    @GetMapping("/success-stories")
    public SuccessStoryModule successStories() {
        return enterpriseModulesService.getSuccessStoryModule();
    }

    @GetMapping("/cms")
    public CmsModule cms() {
        return enterpriseModulesService.getCmsModule();
    }

    @GetMapping("/localization")
    public LocalizationModule localization() {
        return enterpriseModulesService.getLocalizationModule();
    }

    @GetMapping("/whatsapp")
    public WhatsAppModule whatsApp() {
        return enterpriseModulesService.getWhatsAppModule();
    }

    @GetMapping("/seo")
    public SeoModule seo() {
        return enterpriseModulesService.getSeoModule();
    }
}
