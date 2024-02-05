package com.rtecsoft.alpha.confluentschemaregistrydemo.services.schema;

import com.rtecsoft.alpha.confluentschemaregistrydemo.util.SchemaUtil;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.confluent.kafka.schemaregistry.client.rest.entities.Schema;


import java.util.Collection;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class SchemaRegistryServiceImpl implements SchemaRegistryService {

    private final CachedSchemaRegistryClient schemaRegistryClient ; // Set the Schema Registry URL in application.properties

    public int registerJsonSchema(String subject, String jsonSchema) throws Exception {
        // Parse schema from JSON string
        ParsedSchema parsedSchema = SchemaUtil.getParsedSchema(jsonSchema);

        // Register the JSON schema
        int schemaId = schemaRegistryClient.register(subject, parsedSchema);
        log.info("JSON schema registered successfully with ID: " + schemaId);

        // Optionally, you can fetch the registered schema metadata
        SchemaMetadata schemaMetadata = schemaRegistryClient.getLatestSchemaMetadata(subject);
        log.info("Registered schema version: " + schemaMetadata.getVersion());
        return schemaId;
    }

    public ParsedSchema getSchema(int id) throws Exception {
        // Fetch the schema by subject and version
        ParsedSchema schema = schemaRegistryClient.getSchemaBySubjectAndId("", id);
        log.info("Schema fetched successfully: " + schema);
        return schema;
    }


    public ParsedSchema getSchema(String subject, int id) throws Exception {
        // Fetch the schema by subject and version
        ParsedSchema schema = schemaRegistryClient.getSchemaBySubjectAndId(subject, id);
        log.info("Schema fetched successfully: " + schema);
        return schema;
    }

    public SchemaMetadata getSchema(String subject) throws Exception {
        // Fetch the schema by subject and version
        SchemaMetadata schemaMetadata = schemaRegistryClient.getLatestSchemaMetadata(subject);
        log.info("Schema {} fetched successfully: ", schemaMetadata.getId());
        return schemaMetadata;
    }

    public Collection<String> getSubjects() throws Exception {
        // Fetch the schema by subject and version
        var schemas = schemaRegistryClient.getAllSubjects();
        log.info("Subjects {} fetched successfully: ", schemas);
        return schemas;
    }

    public List<Integer> getSchemaVersions(String subject) throws Exception {
        // Fetch the schema by subject and version
        var schemaVersions = schemaRegistryClient.getAllVersions(subject);
        log.info("Schema versions {} fetched successfully: ", schemaVersions);
        return schemaVersions;
    }

    public String getSchemaBySubjectAndVersion(String subject, int version) throws Exception {
        // Fetch the schema by subject and version
        final List<Integer> allVersions = schemaRegistryClient.getAllVersions(subject);
        if (!allVersions.contains(version)) {
            throw new Exception("Version " + version + " not found for subject " + subject);
        }
        final Schema schema = schemaRegistryClient.getByVersion(subject, version, false);
        log.info("Schema {} fetched successfully: ", schema);
        return schema.getSchema();
    }
}
