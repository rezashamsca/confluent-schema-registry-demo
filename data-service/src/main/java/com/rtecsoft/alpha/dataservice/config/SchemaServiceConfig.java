package com.rtecsoft.alpha.dataservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SchemaServiceConfig {
    @Value("${schema-service.url}")
    private String schemaServiceUrl;

    @Bean(name = "schemaServiceRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(schemaServiceUrl)
                .build();
    }
}
