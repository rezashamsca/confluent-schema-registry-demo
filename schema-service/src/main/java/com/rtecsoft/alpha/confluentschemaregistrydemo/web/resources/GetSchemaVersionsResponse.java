package com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class GetSchemaVersionsResponse extends ResponseBase {
    private List<Integer> schemaVersions;
}
