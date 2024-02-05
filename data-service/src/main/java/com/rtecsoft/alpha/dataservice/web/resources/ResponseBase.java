package com.rtecsoft.alpha.dataservice.web.resources;

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
    protected String message;
}
