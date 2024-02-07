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
        log.info("getSubjects");
        var subjectsResponse = schemaServiceApi.getSubjects();
        return subjectsResponse.block();
    }

    public GetSchemaVersionsResponse getSchemaVersions(String subject) {
        log.info("getSchemaVersions");
        var response = schemaServiceApi.getSchemaVersionsBySubject(subject);
        return response.block();
    }

    public GetSchemaResponse getSchemaBySubjectAndVersion(String subject, Integer version) {
        log.info("getSchemaBySubjectAndVersion");
        return schemaServiceApi.getSchemaBySubjectAndVersion(subject, version).block();
    }
}
