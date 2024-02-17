package com.rtecsoft.alpha.dataservice.web;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.rtecsoft.alpha.dataservice.util.TestUtil;
import com.rtecsoft.alpha.dataservice.web.resources.DataRequest;
import com.rtecsoft.alpha.dataservice.web.resources.DataResponse;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSchemaResponse;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSubjectsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 8095)
@ActiveProfiles("test")
public class DataControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testData() {
        // Setup WireMock to stub the external service
        stubFor(post(urlEqualTo("/api/v1/data/action"))
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"status\": \"SUCCESS\", \"message\": \"\", " +
                        "\"objectId\": \"65c41fcbdc519d4314c86dd8\" }")));

        stubFor(get(urlEqualTo("/api/v1/schemas/subjects"))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withJsonBody(getSubjectsResponse())));

        stubFor(get(urlPathMatching("/api/v1/schemas/schema/.*"))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withJsonBody(getSchemaResponse())));


        // Create a request
        DataRequest request = DataRequest.builder()
                .schemaSubject("test.schema.json")
                .schemaVersion(1)
                .action("create")
                .payload(getDataPayload())
                .build();

        // Make a POST request to /api/v1/data
        ResponseEntity<DataResponse> response = restTemplate.postForEntity("/api/v1/data/action", request, DataResponse.class);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getStatus().getSuccess());
    }

    private static Map<String, Object> getDataPayload() {
        return Map.of(
                "testMessage", "This is a test message",
                "testNumber", 30);
    }

    private static JsonNode getSubjectsResponse() {
        return TestUtil.getObject("subjects-test.json", GetSubjectsResponse.class);
    }

    private static JsonNode getSchemaResponse() {
        return TestUtil.getObject("schema-test.json", GetSchemaResponse.class);
    }

    private static JsonNode getPostDataInsert() {
        return TestUtil.getObject("data-response-test.json", DataResponse.class);
    }
}