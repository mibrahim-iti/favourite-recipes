package com.assessment.favouriterecipes.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class AudienceValidatorTest {

    public static final String TEST_AUDIENCE = "testAudience";

    @Test
    void testAudienceValidatorValidateError() {

        final var audienceValidator = new AudienceValidator(TEST_AUDIENCE);
        final var jwt = mock(Jwt.class);

        when(jwt.getAudience()).thenReturn(new ArrayList<>());
        final var result = audienceValidator.validate(jwt);


        assertTrue(result.hasErrors());
    }

    @Test
    void testAudienceValidatorValidateSuccess() {

        final var audienceValidator = new AudienceValidator(TEST_AUDIENCE);
        final var jwt = mock(Jwt.class);

        when(jwt.getAudience()).thenReturn(List.of(TEST_AUDIENCE));
        final var result = audienceValidator.validate(jwt);


        assertFalse(result.hasErrors());
    }

}