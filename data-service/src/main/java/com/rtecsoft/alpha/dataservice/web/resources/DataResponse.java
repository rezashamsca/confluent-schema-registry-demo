package com.rtecsoft.alpha.dataservice.web.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter 
@NoArgsConstructor
@SuperBuilder
public class DataResponse extends ResponseBase {
    private String objectId;
}
