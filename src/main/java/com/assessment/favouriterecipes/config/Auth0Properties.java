package com.assessment.favouriterecipes.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth0")
public class Auth0Properties {

    private String domain;

    private String clientId;

    private String issuerUri;

    private String callbackUrl;

    private String audience;

    private String emailClaimKey;

}
