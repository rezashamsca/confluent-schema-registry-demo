package com.rtecsoft.alpha.dataservice.web;


import com.rtecsoft.alpha.dataservice.web.resources.DataRequest;
import com.rtecsoft.alpha.dataservice.web.resources.DataResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
@ActiveProfiles("test")
public class DataControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testData() {
        // Wiremock stubs are located in src/test/resources/mappings folder

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
                "testMessage", "This is the second test message",
                "testNumber", 30);
    }
}