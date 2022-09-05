package com.assessment.favouriterecipes.audit;

import com.assessment.favouriterecipes.config.Auth0Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<String> {

    private final Auth0Properties auth0Properties;

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();
        return Optional.of(jwt.getClaim(auth0Properties.getEmailClaimKey()));
    }

}