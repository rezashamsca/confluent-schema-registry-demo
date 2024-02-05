package com.rtecsoft.alpha.dataservice.web.resources;

import com.rtecsoft.alpha.dataservice.util.DataUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.util.Map;


@Getter
@Setter
@Builder
public class DataRequest {
    private String id;
    private Integer schemaVersion;
    @NotNull
    private String schemaSubject;
    @NotNull
    private String action;
    @NotNull
    private Map<String, Object> payload;

    public String payloadToString() {
        return DataUtil.mapToString(payload);
    }
}
