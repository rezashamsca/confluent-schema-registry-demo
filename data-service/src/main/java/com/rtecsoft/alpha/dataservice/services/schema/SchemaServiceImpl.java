package com.rtecsoft.alpha.dataservice.services.schema;

import com.rtecsoft.alpha.openapi.schemaservice.api.SchemaServiceApi;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSchemaResponse;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSchemaVersionsResponse;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSubjectsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("schemaServiceImpl")
public class SchemaServiceImpl {
    private final SchemaServiceApi schemaServiceApi;

    public SchemaServiceImpl() {
        this.schemaServiceApi = new SchemaServiceApi();
    }

    public GetSubjectsResponse getSubjects() {
        log.info("getSubjects method");
        return schemaServiceApi.getSubjects()
                .onErrorResume(e -> {
                    log.info("Error getting subjects: " + e.getMessage());
                    return null;
                }).block();
    }

    public GetSchemaVersionsResponse getSchemaVersions(String subject) {
        log.info("getSchemaVersions");
        return schemaServiceApi.getSchemaVersionsBySubject(subject)
                .onErrorResume(e -> {
                    log.info("Error getting schema versions: " + e.getMessage());
                    return null;
                }).block();

    }

    public GetSchemaResponse getSchemaBySubjectAndVersion(String subject, Integer version) {
        log.info("getSchemaBySubjectAndVersion");
        return schemaServiceApi.getSchemaBySubjectAndVersion(subject, version)
                .onErrorResume(e -> {
                    log.info("Error getting schema: " + e.getMessage());
                    return null;
                }).block();

    }
}
