package com.example.demo.service;

import com.example.demo.model.AdminOverview;
import com.example.demo.model.AdminQueueItem;
import com.example.demo.model.Conversation;
import com.example.demo.model.Hero;
import com.example.demo.model.HomePayload;
import com.example.demo.model.MatchProfile;
import com.example.demo.model.Metric;
import com.example.demo.model.Plan;
import com.example.demo.model.ProfileChecklistItem;
import com.example.demo.model.ProfileStudio;
import com.example.demo.model.SafetyHub;
import com.example.demo.model.SuccessStory;
import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MatrimonyContentService {

    private final List<MatchProfile> profiles = List.of(
            new MatchProfile(
                    "KTY-1021",
                    "Aarohi Sharma",
                    28,
                    "Bengaluru",
                    "Product Designer",
                    "NID Ahmedabad",
                    "Brahmin",
                    "Hindu",
                    "5'5\"",
                    "28 LPA",
                    "Calm, creative, and family-oriented. Loves Carnatic playlists, weekend treks, and intentional conversations.",
                    96,
                    "Serious",
                    "Parents verified and profile active today",
                    List.of("Vegetarian", "Wants children", "Pet friendly"),
                    List.of("Aligned on faith and family goals", "Strong communication compatibility", "Shared preference for Bengaluru base")
            ),
            new MatchProfile(
                    "KTY-1097",
                    "Neel Verma",
                    31,
                    "Pune",
                    "Civil Services Officer",
                    "Delhi University",
                    "Kayastha",
                    "Hindu",
                    "5'10\"",
                    "Government Grade A",
                    "Grounded, well-read, and deeply respectful. Looking for a partner who values empathy, learning, and shared rituals.",
                    93,
                    "Family-led",
                    "Recently completed background checks",
                    List.of("Non-smoker", "Family-based search", "Travels for work"),
                    List.of("Strong family-verification signal", "High stability and trust markers", "Shared long-term settlement preference")
            ),
            new MatchProfile(
                    "KTY-1104",
                    "Samaira Khan",
                    27,
                    "Dubai / Lucknow",
                    "Financial Analyst",
                    "IIM Kozhikode",
                    "Sunni",
                    "Muslim",
                    "5'4\"",
                    "32 LPA",
                    "Warm, articulate, and ambitious. Values honesty, deen, and a home where both careers and care can thrive.",
                    91,
                    "Open to Relocation",
                    "Prefers video calls before family introductions",
                    List.of("NRI", "Career supportive", "Open to relocation"),
                    List.of("Strong relocation flexibility", "High compatibility on lifestyle pacing", "Mutual openness to premium-intent flow")
            ),
            new MatchProfile(
                    "KTY-1188",
                    "Rhea Dsouza",
                    29,
                    "Mumbai",
                    "Pediatric Dentist",
                    "MDS, Manipal",
                    "Roman Catholic",
                    "Christian",
                    "5'6\"",
                    "24 LPA",
                    "Cheerful, thoughtful, and rooted in family values. Enjoys coastal getaways, hosting friends, and Sunday church.",
                    89,
                    "Serious",
                    "Available for parent-led calls this weekend",
                    List.of("Faith first", "Active lifestyle", "Cooks for fun"),
                    List.of("Shared value-driven profile depth", "Verified family participation", "Healthy work-life preference match")
            )
    );

    public HomePayload getHomePayload() {
        return new HomePayload(
                new Hero(
                        "Enterprise-grade matrimonial experience",
                        "Trust-first matchmaking for serious families, modern professionals, and high-intent introductions.",
                        "Built from the provided specification as a web-first MVP covering onboarding, discovery, profile trust, protected communication, subscriptions, and admin moderation.",
                        List.of("AI-assisted compatibility", "Selfie, ID, and family verification", "Protected messaging and guided calls", "Premium plans and concierge support")
                ),
                List.of(
                        new Metric("Profiles with selfie verification", "94%"),
                        new Metric("Human-reviewed moderation actions", "100K+"),
                        new Metric("Video dates scheduled monthly", "18K"),
                        new Metric("Successful introductions this quarter", "6,420")
                ),
                List.of(
                        "Create a rich profile with identity checks, family context, and lifestyle preferences.",
                        "Receive AI-ranked introductions with compatibility reasoning and trust markers.",
                        "Unlock secure chat, protected calls, and family-assisted communication when both sides are comfortable.",
                        "Move from interest to verified meeting plans with subscription tools, concierge help, and safety workflows."
                ),
                profiles,
                List.of(
                        new SuccessStory("Aarav + Meera", "Met through family-assisted discovery, moved to voice notes in week one, and got engaged in four months.", "4 months"),
                        new SuccessStory("Rehan + Sana", "Used the NRI-safe introduction path and completed identity, work, and guardian verification before the first trip.", "6 months"),
                        new SuccessStory("Joel + Rhea", "Connected over faith, service, and family routines, then used concierge scheduling to simplify parent meetings.", "5 months")
                )
        );
    }

    public List<MatchProfile> getMatches(String q, String community, String faith, String intent) {
        return profiles.stream()
                .filter(profile -> matches(profile.name() + " " + profile.profession() + " " + profile.location(), q))
                .filter(profile -> matches(profile.community(), community))
                .filter(profile -> matches(profile.faith(), faith))
                .filter(profile -> matches(profile.intent(), intent))
                .toList();
    }

    public MatchProfile getMatchById(String id) {
        return profiles.stream()
                .filter(profile -> profile.id().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));
    }

    public List<Conversation> getConversations() {
        return List.of(
                new Conversation("CNV-1", "Aarohi Sharma", "Mutual match", "Your answer about balancing family and work felt thoughtful. Shall we plan a short video call?", "Online", "10:24 AM", 2),
                new Conversation("CNV-2", "Neel Verma", "Family-approved", "My parents would be happy to connect after your comfort check. No rush at all.", "Scheduled call", "Yesterday", 0),
                new Conversation("CNV-3", "Samaira Khan", "Premium introduction", "I noticed we both value relocation flexibility. Happy to exchange a few voice notes first.", "Away", "Yesterday", 1)
        );
    }

    public List<Plan> getPlans() {
        return List.of(
                new Plan("Classic", "₹2,999", "/ 3 months", false, false, List.of("Unlimited interests", "5 verified chats per day", "Basic compatibility insights", "Contact exchange after mutual consent")),
                new Plan("Prime", "₹6,999", "/ 6 months", true, false, List.of("Priority placement in search", "Video biodata and voice intro", "Advanced filters and horoscope preview", "Dedicated relationship manager callbacks")),
                new Plan("Elite", "₹14,999", "/ 12 months", false, true, List.of("High-intent curated matches", "Family concierge introductions", "Fraud and reputation screening", "Early access to offline community events"))
        );
    }

    public ProfileStudio getProfileStudio() {
        return new ProfileStudio(
                "Kavya Narang",
                78,
                List.of(
                        new ProfileChecklistItem("Identity verification", "Completed"),
                        new ProfileChecklistItem("Family details and values", "Completed"),
                        new ProfileChecklistItem("Lifestyle and preferences", "In progress"),
                        new ProfileChecklistItem("Horoscope and astrology", "Optional"),
                        new ProfileChecklistItem("Voice intro and gallery", "Recommended")
                ),
                List.of("Open to metro cities", "Family-involved introductions", "Career supportive household", "Vegetarian preference"),
                List.of("Add a 30-second voice introduction", "Complete lifestyle answers to improve AI ranking", "Upload one candid and one family-approved portrait")
        );
    }

    public SafetyHub getSafetyHub() {
        return new SafetyHub(
                List.of(
                        new Metric("Escalation SLA", "< 15 min"),
                        new Metric("ID verification layers", "3"),
                        new Metric("Fraud review coverage", "24/7"),
                        new Metric("Sensitive data masking", "Enabled")
                ),
                List.of(
                        "No direct phone or email sharing before mutual consent.",
                        "Voice and video are protected behind trust checks and reporting tools.",
                        "Family-led connections can be enabled without exposing personal contact details."
                ),
                List.of(
                        "Flag suspicious chat, payment request, or coercive language from any conversation.",
                        "Temporarily freeze communication while the moderation team reviews evidence.",
                        "Escalate to a human safety specialist and preserve the interaction log for action."
                )
        );
    }

    public AdminOverview getAdminOverview() {
        return new AdminOverview(
                List.of(
                        new Metric("Pending KYC reviews", "124"),
                        new Metric("High-risk fraud signals", "9"),
                        new Metric("Photo moderation backlog", "38"),
                        new Metric("Subscription disputes", "17")
                ),
                List.of(
                        new AdminQueueItem("KYC reviews", 124, "4-hour SLA", "Aadhaar, passport, and work-email verification pending"),
                        new AdminQueueItem("Photo moderation", 38, "2-hour SLA", "Low-quality or watermarked profile photos flagged by AI"),
                        new AdminQueueItem("Trust complaints", 9, "15-minute SLA", "Urgent reports routed to human moderators immediately"),
                        new AdminQueueItem("Subscription disputes", 17, "1-business-day SLA", "Refund and payment reconciliation workflows")
                ),
                List.of(
                        "Protect contact exchange behind mutual consent and trust thresholds.",
                        "Prioritize NRI fraud prevention and duplicate profile detection.",
                        "Keep moderation, billing, and safety queues visible to operations in one place."
                )
        );
    }

    private boolean matches(String source, String filter) {
        if (filter == null || filter.isBlank()) {
            return true;
        }

        return source.toLowerCase(Locale.ROOT).contains(filter.toLowerCase(Locale.ROOT));
    }
}
