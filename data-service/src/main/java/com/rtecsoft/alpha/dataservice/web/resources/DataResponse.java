package com.rtecsoft.alpha.dataservice.web.resources;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DataResponse extends ResponseBase {
    private String objectId;
}
