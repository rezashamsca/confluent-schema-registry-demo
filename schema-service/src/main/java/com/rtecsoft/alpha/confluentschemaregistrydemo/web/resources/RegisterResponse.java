package com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class RegisterResponse extends ResponseBase {
    private int schemaId;
}
