package com.rtecsoft.alpha.confluentschemaregistrydemo.services.schema;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;

import java.util.Collection;
import java.util.List;

public interface SchemaRegistryService {
    int registerJsonSchema(String subject, String jsonSchema) throws Exception;
    ParsedSchema getSchema(int id) throws Exception;
    ParsedSchema getSchema(String subject, int id) throws Exception;
    SchemaMetadata getSchema(String subject) throws Exception;
    String getSchemaBySubjectAndVersion(String subject, int version) throws Exception;
    Collection<String> getSubjects() throws Exception;
    List<Integer> getSchemaVersions(String subject) throws Exception;
}
