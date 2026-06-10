package com.example.demo.seed;

import com.example.demo.modules.auth.AuthUser;
import com.example.demo.modules.auth.AuthUserRepository;
import com.example.demo.modules.auth.Permission;
import com.example.demo.modules.auth.PermissionRepository;
import com.example.demo.modules.crm.CrmLead;
import com.example.demo.modules.crm.CrmLeadRepository;
import com.example.demo.modules.crm.LeadStatus;
import com.example.demo.modules.fraud.RiskAlert;
import com.example.demo.modules.fraud.RiskAlertRepository;
import com.example.demo.modules.members.MemberProfile;
import com.example.demo.modules.members.MemberProfileRepository;
import com.example.demo.modules.notifications.NotificationItem;
import com.example.demo.modules.notifications.NotificationItemRepository;
import com.example.demo.modules.plans.SubscriptionPlan;
import com.example.demo.modules.plans.SubscriptionPlanRepository;
import com.example.demo.modules.stories.SuccessStoryEntity;
import com.example.demo.modules.stories.SuccessStoryRepository;
import com.example.demo.modules.wallet.WalletTransaction;
import com.example.demo.modules.wallet.WalletTransactionRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            AuthUserRepository authUserRepository,
            PermissionRepository permissionRepository,
            MemberProfileRepository memberProfileRepository,
            CrmLeadRepository crmLeadRepository,
            NotificationItemRepository notificationItemRepository,
            WalletTransactionRepository walletTransactionRepository,
            SuccessStoryRepository successStoryRepository,
            RiskAlertRepository riskAlertRepository,
            SubscriptionPlanRepository subscriptionPlanRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Set<Permission> allPermissions = seedPermissions(permissionRepository);

            ensureMasterAdmin(authUserRepository, passwordEncoder, allPermissions);

            if (memberProfileRepository.count() > 0) {
                return;
            }

            AuthUser admin = new AuthUser();
            admin.setEmail("admin@katyyani.com");
            admin.setPhone("9999999999");
            admin.setFullName("Platform Admin");
            admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
            admin.setRole("ADMIN");
            admin.setTier("ELITE");
            admin.setPermissions(new HashSet<>(allPermissions));
            authUserRepository.save(admin);

            List<MemberProfile> profiles = List.of(
                    createProfile("MEM-1001", "Aarohi Sharma", "aarohi@katyyani.com", "9000000001", 28, "Bengaluru", "Karnataka", "Hindu", "Brahmin", "Product Designer", "NID Ahmedabad", "28 LPA", "Serious", "Calm, creative, and family-oriented.", true, 92, true),
                    createProfile("MEM-1002", "Neel Verma", "neel@katyyani.com", "9000000002", 31, "Pune", "Maharashtra", "Hindu", "Kayastha", "Civil Services Officer", "Delhi University", "Govt Grade A", "Family-led", "Grounded and deeply respectful.", true, 88, true),
                    createProfile("MEM-1003", "Samaira Khan", "samaira@katyyani.com", "9000000003", 27, "Lucknow", "Uttar Pradesh", "Muslim", "Sunni", "Financial Analyst", "IIM Kozhikode", "32 LPA", "Open to Relocation", "Warm, articulate, and ambitious.", true, 90, true),
                    createProfile("MEM-1004", "Rhea Dsouza", "rhea@katyyani.com", "9000000004", 29, "Mumbai", "Maharashtra", "Christian", "Roman Catholic", "Pediatric Dentist", "Manipal", "24 LPA", "Serious", "Cheerful and thoughtful.", false, 76, true)
            );
            memberProfileRepository.saveAll(profiles);

            crmLeadRepository.saveAll(List.of(
                    createLead("Aarohi Sharma", "aarohi@katyyani.com", "Bengaluru", LeadStatus.ENGAGED, "Priya Nair", "ELITE", "Organic", "Schedule family intro review"),
                    createLead("Neel Verma", "neel@katyyani.com", "Pune", LeadStatus.CONTACTED, "Rahul Singh", "PREMIUM", "Referral", "Follow-up on premium upgrade"),
                    createLead("Samaira Khan", "samaira@katyyani.com", "Lucknow", LeadStatus.NEW, "Aisha Khan", "PLATINUM", "Campaign", "Welcome call within 24h")
            ));

            notificationItemRepository.saveAll(List.of(
                    createNotification("Interest Received", "Someone viewed and liked your profile.", "PUSH", "MATCHING", true),
                    createNotification("Subscription Expiry", "Your Prime plan expires in 7 days.", "EMAIL", "BILLING", true),
                    createNotification("KYC Approved", "Your verification is complete.", "SMS", "TRUST", true)
            ));

            walletTransactionRepository.saveAll(List.of(
                    createTransaction("aarohi@katyyani.com", "BONUS", "CREDIT", new BigDecimal("200.00"), "referral", "REF-1001", Instant.now().plusSeconds(15552000)),
                    createTransaction("neel@katyyani.com", "CASHBACK", "CREDIT", new BigDecimal("350.00"), "subscription-cashback", "ORD-9001", null),
                    createTransaction("samaira@katyyani.com", "REFUND", "CREDIT", new BigDecimal("999.00"), "refund", "ORD-9002", null)
            ));

            successStoryRepository.saveAll(List.of(
                    createStory("Aarav + Meera", "aarav-meera", "Delhi", "Met through family-assisted discovery.", "A thoughtful journey from intro call to engagement.", true),
                    createStory("Rehan + Sana", "rehan-sana", "Dubai", "Used NRI-safe introduction path.", "Verification-first flow helped both families feel safe.", true),
                    createStory("Joel + Rhea", "joel-rhea", "Mumbai", "Connected over faith and routines.", "Concierge scheduling simplified parent meetings.", false)
            ));

            riskAlertRepository.saveAll(List.of(
                    createRisk("Duplicate phone verification attempts", "HIGH", "OPEN", "Repeated OTP requests from same IP and device.", "Temporarily suspend and review"),
                    createRisk("Aggressive contact-sharing behavior", "MEDIUM", "UNDER_REVIEW", "Member tried to share direct contact before consent.", "Throttle messaging and warn"),
                    createRisk("Refund abuse pattern", "HIGH", "ESCALATED", "Multiple refund wallet credits across related accounts.", "Escalate to finance + trust")
            ));

            subscriptionPlanRepository.saveAll(List.of(
                    createPlan("Classic", new BigDecimal("2999.00"), 3, "Unlimited interests, compatibility insights", false),
                    createPlan("Prime", new BigDecimal("6999.00"), 6, "Priority search placement, RM callback", true),
                    createPlan("Elite", new BigDecimal("14999.00"), 12, "Curated introductions, concierge support", false)
            ));
        };
    }

    private Set<Permission> seedPermissions(PermissionRepository permissionRepository) {
        List<String> permissionCodes = List.of(
                "AUTH_MANAGE",
                "MEMBER_VIEW",
                "MEMBER_CREATE",
                "MEMBER_UPDATE",
                "MEMBER_DELETE",
                "MATCH_VIEW",
                "MATCH_MANAGE",
                "MESSAGE_VIEW",
                "MESSAGE_MANAGE",
                "PLAN_VIEW",
                "PLAN_MANAGE",
                "WALLET_VIEW",
                "WALLET_MANAGE",
                "NOTIFICATION_VIEW",
                "NOTIFICATION_MANAGE",
                "CRM_VIEW",
                "CRM_MANAGE",
                "FRAUD_VIEW",
                "FRAUD_MANAGE",
                "CMS_VIEW",
                "CMS_MANAGE",
                "STORY_VIEW",
                "STORY_MANAGE",
                "SEO_VIEW",
                "SEO_MANAGE",
                "KYC_VIEW",
                "KYC_MANAGE",
                "ANALYTICS_VIEW",
                "ADMIN_DASHBOARD_VIEW",
                "SYSTEM_CONFIG_MANAGE"
        );

        Set<Permission> permissions = new HashSet<>();
        for (String code : permissionCodes) {
            Permission permission = permissionRepository.findByCode(code).orElseGet(() -> {
                Permission created = new Permission();
                created.setCode(code);
                created.setDescription(code.replace('_', ' '));
                return permissionRepository.save(created);
            });
            permissions.add(permission);
        }
        return permissions;
    }

    private void ensureMasterAdmin(
            AuthUserRepository authUserRepository,
            PasswordEncoder passwordEncoder,
            Set<Permission> allPermissions
    ) {
        AuthUser masterAdmin = authUserRepository.findByEmail("master@admin.com").orElseGet(AuthUser::new);
        masterAdmin.setEmail("master@admin.com");
        masterAdmin.setPhone("9999999998");
        masterAdmin.setFullName("Master Admin");
        masterAdmin.setPasswordHash(passwordEncoder.encode("Password@123"));
        masterAdmin.setRole("MASTER_ADMIN");
        masterAdmin.setTier("MASTER");
        masterAdmin.setPermissions(new HashSet<>(allPermissions));
        authUserRepository.save(masterAdmin);
    }

    private MemberProfile createProfile(String code, String name, String email, String phone, Integer age, String city, String state, String religion, String community, String profession, String education, String income, String intent, String bio, Boolean verified, Integer completion, Boolean active) {
        MemberProfile profile = new MemberProfile();
        profile.setMemberCode(code);
        profile.setFullName(name);
        profile.setEmail(email);
        profile.setPhone(phone);
        profile.setAge(age);
        profile.setCity(city);
        profile.setState(state);
        profile.setReligion(religion);
        profile.setCommunity(community);
        profile.setProfession(profession);
        profile.setEducation(education);
        profile.setAnnualIncome(income);
        profile.setIntent(intent);
        profile.setBio(bio);
        profile.setVerified(verified);
        profile.setProfileCompletion(completion);
        profile.setActive(active);
        return profile;
    }

    private CrmLead createLead(String memberName, String email, String city, LeadStatus status, String rm, String tier, String source, String nextAction) {
        CrmLead lead = new CrmLead();
        lead.setMemberName(memberName);
        lead.setEmail(email);
        lead.setCity(city);
        lead.setStatus(status);
        lead.setRelationshipManager(rm);
        lead.setPriorityTier(tier);
        lead.setSourceChannel(source);
        lead.setNextAction(nextAction);
        return lead;
    }

    private NotificationItem createNotification(String title, String body, String channel, String category, Boolean active) {
        NotificationItem item = new NotificationItem();
        item.setTitle(title);
        item.setBody(body);
        item.setChannel(channel);
        item.setCategory(category);
        item.setActive(active);
        return item;
    }

    private WalletTransaction createTransaction(String email, String walletType, String transactionType, BigDecimal amount, String source, String referenceId, Instant expiresAt) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setUserEmail(email);
        transaction.setWalletType(walletType);
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setSource(source);
        transaction.setReferenceId(referenceId);
        transaction.setExpiresAt(expiresAt);
        return transaction;
    }

    private SuccessStoryEntity createStory(String couple, String slug, String city, String summary, String story, Boolean featured) {
        SuccessStoryEntity entity = new SuccessStoryEntity();
        entity.setCoupleName(couple);
        entity.setSlug(slug);
        entity.setCity(city);
        entity.setSummary(summary);
        entity.setStory(story);
        entity.setFeatured(featured);
        return entity;
    }

    private RiskAlert createRisk(String subject, String severity, String status, String reason, String action) {
        RiskAlert alert = new RiskAlert();
        alert.setSubject(subject);
        alert.setSeverity(severity);
        alert.setStatus(status);
        alert.setReason(reason);
        alert.setRecommendedAction(action);
        return alert;
    }

    private SubscriptionPlan createPlan(String name, BigDecimal price, Integer months, String featureSummary, Boolean featured) {
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setName(name);
        plan.setPrice(price);
        plan.setDurationMonths(months);
        plan.setFeatureSummary(featureSummary);
        plan.setFeatured(featured);
        return plan;
    }
}
