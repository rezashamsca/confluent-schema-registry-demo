package com.rtecsoft.alpha.confluentschemaregistrydemo.util;

import com.rtecsoft.alpha.confluentschemaregistrydemo.TestData;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SchemaUtilTest {
    @Test
    void mapToStringTest() {
        Map<String, Object> sampleMap = Map.of(
                "name", "John",
                "age", 25,
                "isStudent", true
        );

        String result = SchemaUtil.mapToString(sampleMap);
        assertThat(result).contains("\"name\":\"John\"");
    }

    @Test
    void getParsedSchema() throws Exception {
        TestData testData = new TestData();
        Map<String, Object> productSchema = testData.getProductSchema();
        String jsonSchema = SchemaUtil.mapToString(productSchema);
        ParsedSchema parsedSchema = SchemaUtil.getParsedSchema(jsonSchema);
        assertThat(parsedSchema).isNotNull();
    }
}