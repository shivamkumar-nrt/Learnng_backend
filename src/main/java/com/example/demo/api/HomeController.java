package com.example.demo.api;

import com.example.demo.model.AdminOverview;
import com.example.demo.model.Conversation;
import com.example.demo.model.HomePayload;
import com.example.demo.model.MatchProfile;
import com.example.demo.model.Plan;
import com.example.demo.model.ProfileStudio;
import com.example.demo.model.SafetyHub;
import com.example.demo.service.MatrimonyContentService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    private final MatrimonyContentService matrimonyContentService;

    public HomeController(MatrimonyContentService matrimonyContentService) {
        this.matrimonyContentService = matrimonyContentService;
    }

    @GetMapping("/home")
    public HomePayload home() {
        return matrimonyContentService.getHomePayload();
    }

    @GetMapping("/matches")
    public List<MatchProfile> matches(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String community,
            @RequestParam(required = false) String faith,
            @RequestParam(required = false) String intent
    ) {
        return matrimonyContentService.getMatches(q, community, faith, intent);
    }

    @GetMapping("/matches/{id}")
    public MatchProfile matchDetails(@PathVariable String id) {
        return matrimonyContentService.getMatchById(id);
    }

    @GetMapping("/conversations")
    public List<Conversation> conversations() {
        return matrimonyContentService.getConversations();
    }

    @GetMapping("/plans")
    public List<Plan> plans() {
        return matrimonyContentService.getPlans();
    }

    @GetMapping("/profile-studio")
    public ProfileStudio profileStudio() {
        return matrimonyContentService.getProfileStudio();
    }

    @GetMapping("/safety")
    public SafetyHub safety() {
        return matrimonyContentService.getSafetyHub();
    }

    @GetMapping("/admin/overview")
    public AdminOverview adminOverview() {
        return matrimonyContentService.getAdminOverview();
    }
}
