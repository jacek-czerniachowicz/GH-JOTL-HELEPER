package com.gloomhaven.helper.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
           contact = @Contact(
                   email = "contact@ghhelper.com"
           ),
            description = "Rest API for card board game 'Gloomhaven Jaws of the Lion' helper application",
            title = "OpenApi specification - GHHELPER",
            version = "1.0",

            license =  @License(
                    name = "License name",
                    url = "https://this-license-url.com"
            ),
            termsOfService = "Therms of service"
    ),
        servers = {
            @Server(
                    description = "Local environment",
                    url = "http://localhost:8080"
            )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {
}
