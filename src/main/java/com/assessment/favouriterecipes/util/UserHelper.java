package com.assessment.favouriterecipes.util;

import com.assessment.favouriterecipes.audit.SecurityAuditorAware;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserHelper {

    private final SecurityAuditorAware securityAuditorAware;

    @Generated
    public Optional<String> getLoggedInUsername() {

        return securityAuditorAware.getCurrentAuditor();
    }

    @Generated
    public String getLoggedInUsernameValue() {

        final Optional<String> loggedInUsernameOptional = getLoggedInUsername();
        return loggedInUsernameOptional.isPresent() ? loggedInUsernameOptional.get() : null;
    }

}
