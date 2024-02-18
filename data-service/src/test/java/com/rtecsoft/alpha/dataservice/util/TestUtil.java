package com.rtecsoft.alpha.dataservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
}