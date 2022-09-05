package com.assessment.favouriterecipes.util;

import com.assessment.favouriterecipes.stub.UserHelperStub;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

public class JwtUtils {

    private JwtUtils() {

        throw new IllegalStateException("Utility class");
    }

    @NotNull
    public static Jwt createTestJwtToken(String claimKey) {

        var userId = "TEST_USER_ID";
        var userName = UserHelperStub.TEST_EMAIL;
        var audience = "https://test.com/api/v2/";

        return Jwt.withTokenValue("token-for-testing-purpose")
                .header("typ", "JWT")
                .header("alg", "RS256")
                .header("kid", "test")
                .claim(claimKey, userName)
                .claim("http://mibrahim.tech/email_verified", Boolean.TRUE)
                .claim("iss", "https://test.com/")
                .claim("sub", "auth0|5407fa85414429fb5921f025")
                .claim("aud", audience)
                .claim("azp", "s3oo9GhuPRjnrfC9GNfBLMzsHVB5mQtNC")
                .expiresAt(Instant.now().minusSeconds(60))
                .subject(userId)
                .audience(Set.of(audience))
                .build();
    }

}
