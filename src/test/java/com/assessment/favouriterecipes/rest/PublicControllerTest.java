package com.assessment.favouriterecipes.rest;

import com.assessment.favouriterecipes.config.Auth0Properties;
import com.assessment.favouriterecipes.constant.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class PublicControllerTest {

    private Auth0Properties auth0Properties;

    private PublicController publicController;

    private final String DOMAIN = "domain";

    private final String AUDIENCE = "audience";

    private final String CLIENT_ID = "client-id";

    private final String CALLBACK_URL = "callback-url";

    @BeforeEach
    public void init() {

        auth0Properties = new Auth0Properties();
        auth0Properties.setDomain(DOMAIN);
        auth0Properties.setAudience(AUDIENCE);
        auth0Properties.setClientId(CLIENT_ID);
        auth0Properties.setCallbackUrl(CALLBACK_URL);

        publicController = new PublicController(auth0Properties);
    }

    private String buildAuth0RedirectLoginUrl() {

        return String.format("https://%s/authorize?response_type=token&client_id=%s&redirect_uri=%s&scope=openid,profile,email&audience=%s",
                DOMAIN, CLIENT_ID, CALLBACK_URL, AUDIENCE);
    }

    @Test
    void givenValidRequest_whenWelcomePageRequested_thenRedirectToWelcomePageSuccessfullyHappen() throws IOException {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final var mockedModel = mock(Model.class);
        when(mockedModel.addAttribute(any(String.class), any(String.class))).thenReturn(mockedModel);
        final var response = publicController.welcome(mockedModel);

        assertEquals("welcome", response);
        verify(mockedModel).addAttribute(Constants.AUTH0_AUTHORIZE_URL, buildAuth0RedirectLoginUrl());
    }

}
