package com.rtecsoft.alpha.confluentschemaregistrydemo.util;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.json.JsonSchema;

import java.util.Map;

public class SchemaUtil {
    public static ParsedSchema getParsedSchema(String jsonSchema) throws Exception {
        return new JsonSchema(jsonSchema);
    }

    public static String mapToString(Map<String, Object> map) {
        return mapToStringRecursive(map, new StringBuilder());
    }

    public static String mapToStringRecursive(Map<String, Object> map, StringBuilder stringBuilder) {
        stringBuilder.append("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            stringBuilder.append("\"").append(entry.getKey()).append("\":");

            Object value = entry.getValue();
            if (value instanceof String) {
                stringBuilder.append("\"").append(value).append("\"");
            } else if (value instanceof Map) {
                mapToStringRecursive((Map<String, Object>) value, stringBuilder);
            } else {
                stringBuilder.append(value);
            }

            stringBuilder.append(",");
        }

        if (stringBuilder.length() > 1) {
            // Remove the trailing comma
            stringBuilder.setLength(stringBuilder.length() - 1);
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

}
