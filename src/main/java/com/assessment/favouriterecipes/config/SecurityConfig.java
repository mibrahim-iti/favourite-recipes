package com.assessment.favouriterecipes.config;

import com.assessment.favouriterecipes.util.AudienceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final Auth0Properties auth0Properties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // allow all users to access the home pages and the static images directory
                .mvcMatchers("*").permitAll()
                .antMatchers("/css/**", "/js/**", "/accesstoken**", "/api/v1/public/**", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll()
                // all other requests must be authenticated
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .decoder(jwtDecoder());

        return http.build();
    }


    JwtDecoder jwtDecoder() {

        OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(auth0Properties.getAudience());
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(auth0Properties.getIssuerUri());
        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);

        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(auth0Properties.getIssuerUri());
        jwtDecoder.setJwtValidator(validator);
        return jwtDecoder;
    }

}
