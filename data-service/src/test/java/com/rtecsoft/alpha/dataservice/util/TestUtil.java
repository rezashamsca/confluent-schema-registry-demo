package com.rtecsoft.alpha.dataservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtecsoft.alpha.dataservice.web.resources.DataResponse;
import com.rtecsoft.alpha.dataservice.web.resources.ResponseBase;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSchemaResponse;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSubjectsResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class TestUtil {

    public static String loadJson(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON from " + path, e);
        }
    }

    public static <T> JsonNode getObject(String path, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String jsonContent = loadJson(path);
            T objectValue =  objectMapper.readValue(jsonContent, clazz);
            return objectMapper.valueToTree(objectValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to load JSON from " + path, e);
        }
    }

    @Test
    void getObjectTest() {
        JsonNode dataResponseJsonNode = getObject("data-response-test.json", DataResponse.class);
        assertNotNull(dataResponseJsonNode);
        JSONAssert.assertEquals("{\"status\":\"SUCCESS\",\"message\":null,\"objectId\":\"65c41fcbdc519d4314c86dd8\"}", dataResponseJsonNode.toString(), false);
        JsonNode subjectsJsonNode = getObject("subjects-test.json", GetSubjectsResponse.class);
        assertNotNull(subjectsJsonNode);
        JSONAssert.assertEquals("{\"status\":\"SUCCESS\",\"message\":null,\"subjects\":[\"test.schema.json\"]}", subjectsJsonNode.toString(), false);
        JsonNode schemaJsonNode = getObject("schema-test.json", GetSchemaResponse.class);
        assertNotNull(schemaJsonNode);
        final String schema = schemaJsonNode.get("schema").asText();
        assertEquals("{\"$id\":\"https://com.rtecsoft.alpha/test.schema.json\",\"$schema\":\"https://json-schema.org/draft-07/schema\",\"title\":\"Test\",\"type\":\"object\",\"properties\":{\"testMessage\":{\"type\":\"string\"},\"testNumber\":{\"type\":\"integer\",\"minimum\":20}}}", schema);
    }
}