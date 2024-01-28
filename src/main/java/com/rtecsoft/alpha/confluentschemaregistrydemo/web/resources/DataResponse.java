package com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DataResponse {
    public enum StatusEnum {
        SUCCESS,
        ERROR
    }

    private StatusEnum status;
    private String message;
    private String objectId;
}
