package com.assessment.favouriterecipes.rest;

import com.assessment.favouriterecipes.config.Auth0Properties;
import com.assessment.favouriterecipes.constant.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
@Tag(name = Constants.PUBLIC_APIS, description = Constants.PUBLIC_APIS_DESC)
public class PublicController {

    private final Auth0Properties auth0Properties;

    @GetMapping("/welcome")
    public String welcome(Model model) {

        model.addAttribute(Constants.AUTH0_AUTHORIZE_URL, buildAuth0RedirectLoginUrl());

        return "welcome";
    }

    private String buildAuth0RedirectLoginUrl() {

        return String.format("https://%s/authorize?response_type=token&client_id=%s&redirect_uri=%s&scope=openid,profile,email&audience=%s",
                auth0Properties.getDomain(), auth0Properties.getClientId(), auth0Properties.getCallbackUrl(), auth0Properties.getAudience());
    }

}
