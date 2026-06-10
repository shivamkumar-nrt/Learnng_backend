package com.example.demo.model;

import java.util.List;

public final class EnterpriseModulesPayload {

    private EnterpriseModulesPayload() {
    }

    public record PlatformOverview(
            Summary summary,
            ArchitectureOverview architecture,
            CrmModule crm,
            NotificationModule notifications,
            WalletModule wallet,
            AdvertisementModule advertisements,
            FraudRiskModule fraudRisk,
            SuccessStoryModule successStories,
            CmsModule cms,
            LocalizationModule localization,
            WhatsAppModule whatsApp,
            SeoModule seo
    ) {
    }

    public record AdminPanelConfig(
            String title,
            String subtitle,
            List<AdminNavSection> sections
    ) {
    }

    public record AdminNavSection(
            String title,
            List<AdminNavItem> items
    ) {
    }

    public record AdminNavItem(
            String key,
            String label,
            String href,
            String description
    ) {
    }

    public record Summary(
            String version,
            String timeline,
            String totalApis,
            String totalTables,
            String microservices,
            List<String> priorities
    ) {
    }

    public record ArchitectureOverview(
            List<String> microservices,
            List<String> corePlatforms,
            List<String> scaleEstimates,
            List<String> timelinePhases,
            List<String> rbacDomains
    ) {
    }

    public record CrmModule(
            List<LeadCard> leads,
            List<FollowUpRule> followUpRules,
            List<ReportCard> reports,
            List<String> communicationCapabilities
    ) {
    }

    public record LeadCard(
            String segment,
            int count,
            String assignmentRule,
            String nextAction
    ) {
    }

    public record FollowUpRule(
            String action,
            String trigger,
            String serviceLevel
    ) {
    }

    public record ReportCard(
            String name,
            String description,
            String frequency
    ) {
    }

    public record NotificationModule(
            List<ChannelCard> channels,
            List<TemplateCard> templates,
            List<String> preferences,
            List<String> broadcastFeatures
    ) {
    }

    public record ChannelCard(
            String channel,
            String provider,
            String deliveryState
    ) {
    }

    public record TemplateCard(
            String event,
            boolean pushEnabled,
            boolean smsEnabled,
            boolean emailEnabled
    ) {
    }

    public record WalletModule(
            List<WalletCard> wallets,
            List<String> creditEvents,
            List<String> usageRules,
            List<LedgerField> ledger
    ) {
    }

    public record WalletCard(
            String type,
            String description,
            String balance,
            String usage
    ) {
    }

    public record LedgerField(
            String field,
            String type,
            String description
    ) {
    }

    public record AdvertisementModule(
            List<AdPlacement> placements,
            List<String> campaignFeatures,
            List<String> analytics
    ) {
    }

    public record AdPlacement(
            String name,
            String description,
            String audience
    ) {
    }

    public record FraudRiskModule(
            List<RiskSignal> signals,
            List<String> preventionRules,
            List<String> reviewOutcomes
    ) {
    }

    public record RiskSignal(
            String signal,
            String severity,
            String action
    ) {
    }

    public record SuccessStoryModule(
            List<StoryWorkflow> workflows,
            List<String> adminTools,
            List<String> growthBenefits
    ) {
    }

    public record StoryWorkflow(
            String stage,
            String description
    ) {
    }

    public record CmsModule(
            List<String> managedPages,
            List<String> editorCapabilities,
            List<String> governance
    ) {
    }

    public record LocalizationModule(
            List<LanguagePack> languages,
            List<String> translationFeatures,
            List<String> rolloutRules
    ) {
    }

    public record LanguagePack(
            String language,
            String status,
            String surfaces
    ) {
    }

    public record WhatsAppModule(
            List<String> entryPoints,
            List<String> automationFlows,
            List<String> complianceRules
    ) {
    }

    public record SeoModule(
            List<String> landingPages,
            List<String> metadataStrategy,
            List<String> performanceSignals
    ) {
    }
}
