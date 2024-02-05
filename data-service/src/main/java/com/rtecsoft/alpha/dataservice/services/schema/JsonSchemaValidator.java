package com.rtecsoft.alpha.dataservice.services.schema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonSchemaValidator {
    public static void validateJson(String jsonPayload, String jsonSchema) {
        JSONObject schemaObject = new JSONObject(new JSONTokener(jsonSchema));
        Schema schema = SchemaLoader.load(schemaObject);

        JSONObject payloadObject = new JSONObject(new JSONTokener(jsonPayload));
        schema.validate(payloadObject);  // throws a ValidationException if this object is invalid
    }
}