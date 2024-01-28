package com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources;

import com.rtecsoft.alpha.confluentschemaregistrydemo.util.SchemaUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class RegisterRequest {
    private String subject;
    private Map<String, Object> jsonSchema;

    public String jsonSchemaToString() {
        return SchemaUtil.mapToString(jsonSchema);
    }
}
