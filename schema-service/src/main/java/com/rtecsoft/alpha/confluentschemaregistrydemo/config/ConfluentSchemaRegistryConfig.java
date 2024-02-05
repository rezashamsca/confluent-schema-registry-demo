package com.rtecsoft.alpha.confluentschemaregistrydemo.config;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfluentSchemaRegistryConfig {
    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl; // Set the Schema Registry URL in application.properties

    @Bean
    public CachedSchemaRegistryClient schemaRegistryClient() {
        return new CachedSchemaRegistryClient(schemaRegistryUrl, 10);
    }
}
