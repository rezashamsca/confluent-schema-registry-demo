package com.rtecsoft.alpha.confluentschemaregistrydemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestData {
    public Map<String, Object> getProductSchema() throws IOException {
        var productSchemaStream = getResourceInputStream("product.schema.json");
        return getMap(productSchemaStream);
    }

    public InputStream getResourceInputStream(String resourceName) {
        return getClass().getClassLoader().getResourceAsStream(resourceName);
    }

    public Map<String, Object> getMap(InputStream inputStream) throws IOException {
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<>() {
        };
        return new ObjectMapper().readValue(inputStream, typeRef);
    }
}
