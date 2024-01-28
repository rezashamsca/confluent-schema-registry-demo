package com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class ResponseBase {
    public enum Status {
        SUCCESS,
        FAILURE
    }

    protected Status status;
}
