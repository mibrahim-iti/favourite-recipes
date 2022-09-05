package com.assessment.favouriterecipes.integration.common;

import com.assessment.favouriterecipes.config.Auth0Properties;
import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.util.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class IntegrationRestTest extends AbstractIntegrationTest {

    protected MockMvc mockMvc;

    @Autowired
    private Flyway flyway;

    @Autowired
    private Auth0Properties auth0Properties;

    @Autowired
    private WebApplicationContext controller;

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRequestPostProcessor;


    @BeforeEach
    public void setup() {

        flyway.clean();
        flyway.migrate();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(controller)
                .apply(springSecurity())
                .build();

        jwtRequestPostProcessor = getJwt();

        final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtUtils.createTestJwtToken(auth0Properties.getEmailClaimKey()));
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);

    }

    @NotNull
    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor getJwt() {

        return jwt().jwt(JwtUtils.createTestJwtToken(auth0Properties.getEmailClaimKey()));
    }

    @NotNull
    protected static String errorCodeToJson(final ErrorCode errorCode, final String fieldName) {

        return "{" +
                "  \"code\": \"" + errorCode.getCode() + "\"," +
                "  \"messageKey\": \"" + errorCode.getMessageKey() + "\"," +
                "  \"value\": \"" + fieldName + "\"" +
                "}";
    }

    @NotNull
    protected static String toJson(final Object obj) {

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
