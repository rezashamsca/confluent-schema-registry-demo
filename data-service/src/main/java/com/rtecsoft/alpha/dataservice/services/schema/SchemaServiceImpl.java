package com.rtecsoft.alpha.dataservice.services.schema;

import com.rtecsoft.alpha.openapi.schemaservice.api.SchemaServiceApi;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSchemaResponse;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSchemaVersionsResponse;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSubjectsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service("schemaServiceV3Impl")
public class SchemaServiceImpl implements SchemaServiceApi {
    private @Qualifier("schemaServiceRestTemplate") final RestTemplate restTemplate;
    private static final String SCHEMA_SERVICE_API_V1 = "/api/v1/schemas";

    public ResponseEntity<GetSubjectsResponse> getSubjects() {
        log.info("getSubjects");
        final String url = SCHEMA_SERVICE_API_V1 + "/subjects";
        return restTemplate.exchange(url, HttpMethod.GET, null, GetSubjectsResponse.class);
    }

    public ResponseEntity<GetSchemaVersionsResponse> getSchemaVersions(String subject) {
        log.info("getSchemaVersions");
        final String url = SCHEMA_SERVICE_API_V1 + "/schema/versions/" + subject;
        return restTemplate.exchange(url, HttpMethod.GET, null, GetSchemaVersionsResponse.class);
    }

    public ResponseEntity<GetSchemaResponse> getSchemaBySubjectAndVersion(String subject, Integer version) {
        log.info("getSchemaBySubjectAndVersion");
        final String url = SCHEMA_SERVICE_API_V1 + "/schema/" + subject + "/" + version;
        return restTemplate.exchange(url, HttpMethod.GET, null, GetSchemaResponse.class);
    }
}
