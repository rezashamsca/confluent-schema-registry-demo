package com.rtecsoft.alpha.confluentschemaregistrydemo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "schema-service-api",
                version = "0.0.1",
                description = "Schema Service API"
        )
)
public class OpenAPIConfig {
}

