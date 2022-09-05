package com.assessment.favouriterecipes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "${swagger.info.apiTitle}", version = "${swagger.info.apiVersion}", description = "${swagger.info.apiDescription}",
        contact = @Contact(name = "${swagger.contact.apiContactName}", email = "${swagger.contact.apiContactEmail}", url = "${swagger.contact.apiContactUrl}")))
@SecurityScheme(
        name = OpenApiConfig.OAUTH_SCHEME_NAME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    public static final String OAUTH_SCHEME_NAME = "bearerAuth";

}
