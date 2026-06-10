package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.api.HomeController;
import com.example.demo.api.EnterpriseModulesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HomeControllerTest {

    @Autowired
    private HomeController homeController;

    @Autowired
    private EnterpriseModulesController enterpriseModulesController;

    @Test
    void homeEndpointReturnsConfiguredPayload() {
        var payload = homeController.home();

        assertThat(payload.hero().title()).isNotBlank();
        assertThat(payload.featuredMatches()).hasSize(4);
    }

    @Test
    void matchesEndpointCanFilterByFaith() {
        var matches = homeController.matches(null, null, "Muslim", null);

        assertThat(matches)
                .hasSize(1)
                .first()
                .extracting("name")
                .isEqualTo("Samaira Khan");
    }

    @Test
    void enterpriseOverviewExposesExtendedModules() {
        var overview = enterpriseModulesController.overview();

        assertThat(overview.summary().version()).contains("2.0");
        assertThat(overview.crm().leads()).isNotEmpty();
        assertThat(overview.notifications().channels()).isNotEmpty();
        assertThat(overview.architecture().microservices()).hasSizeGreaterThanOrEqualTo(5);
    }
}
