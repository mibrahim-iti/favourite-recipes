package com.assessment.favouriterecipes.integration.common;

import com.assessment.favouriterecipes.config.Auth0Properties;
import com.assessment.favouriterecipes.util.JwtUtils;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@SpringBootTest
public abstract class IntegrationServiceAbstractTest extends AbstractIntegrationTest {

    @Autowired
    private Auth0Properties auth0Properties;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setup() {

        flyway.clean();
        flyway.migrate();

        final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtUtils.createTestJwtToken(auth0Properties.getEmailClaimKey()));
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
    }

}

