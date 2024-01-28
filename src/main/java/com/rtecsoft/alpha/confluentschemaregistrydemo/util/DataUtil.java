package com.rtecsoft.alpha.confluentschemaregistrydemo.util;

import com.rtecsoft.alpha.confluentschemaregistrydemo.domain.DataEntities;
import com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources.DataRequest;

import java.util.List;

public class DataUtil {
    public static final List<String> RETURNABLE_ACTION = List.of("create", "update", "delete");

    public static DataEntities map(DataRequest request) {
        return DataEntities.builder()
                .schemaSubject(request.getSchemaSubject())
                .schemaVersion(request.getSchemaVersion())
                .payload(request.payloadToString())
                .build();
    }

    public static boolean isReturnable(String action) {
        return RETURNABLE_ACTION.contains(action);
    }

    public static boolean isVersionIfEmpty(Integer version) {
        return version == null || version < 1;
    }
}
