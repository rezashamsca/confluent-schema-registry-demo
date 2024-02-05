package com.rtecsoft.alpha.dataservice.web.resources;

import com.rtecsoft.alpha.dataservice.domain.DataEntities;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GetDataResponse extends ResponseBase {
    private DataEntities data;
}
