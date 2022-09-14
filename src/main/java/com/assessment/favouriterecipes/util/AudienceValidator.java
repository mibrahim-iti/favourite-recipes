package com.assessment.favouriterecipes.util;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final String audience;

    public AudienceValidator(String audience) {

        Assert.hasText(audience, "audience is null or empty");
        this.audience = audience;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {

        // To be enhanced for a better security check
        var audiences = jwt.getAudience();
        if (audiences.contains(this.audience)) {
            return OAuth2TokenValidatorResult.success();
        }
        var err = new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN);

        return OAuth2TokenValidatorResult.failure(err);
    }

}
