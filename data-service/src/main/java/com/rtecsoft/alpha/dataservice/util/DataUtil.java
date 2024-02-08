package com.rtecsoft.alpha.dataservice.util;

import com.rtecsoft.alpha.dataservice.domain.DataEntities;
import com.rtecsoft.alpha.dataservice.web.resources.DataRequest;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataUtil {
    public static final List<String> RETURNABLE_ACTION = List.of("create", "update", "delete");

    public static DataEntities map(DataRequest request) {
        return DataEntities.builder()
                .schemaSubject(request.getSchemaSubject())
                .schemaVersion(request.getSchemaVersion())
                .createdDate(DateTime.now().toDate())
                .payload(request.payloadToString())
                .build();
    }

    public static boolean isReturnable(String action) {
        return RETURNABLE_ACTION.contains(action);
    }

    public static boolean isVersionIfEmpty(DataRequest request) {
        return isVersionIfEmpty(request.getSchemaVersion());
    }


    public static boolean isVersionIfEmpty(Integer version) {
        return version == null || version < 1;
    }

    public static String mapToString(Map<String, Object> map) {
        return mapToStringRecursive(map, new StringBuilder());
    }

    public static String mapToStringRecursive(Map<String, Object> map, StringBuilder stringBuilder) {
        stringBuilder.append("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            stringBuilder.append("\"").append(entry.getKey()).append("\":");

            Object value = entry.getValue();
            if (value instanceof String) {
                stringBuilder.append("\"").append(value).append("\"");
            } else if (value instanceof Map) {
                mapToStringRecursive((Map<String, Object>) value, stringBuilder);
            } else {
                stringBuilder.append(value);
            }

            stringBuilder.append(",");
        }

        if (stringBuilder.length() > 1) {
            // Remove the trailing comma
            stringBuilder.setLength(stringBuilder.length() - 1);
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public static <T> boolean isResponseSuccessful(ResponseEntity<T> responseEntity, Class<T> tClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var getStatusMethod = tClass.getMethod("getStatus");
            var result = (String) getStatusMethod.invoke(responseEntity.getBody()).toString();
            // Add is successful to base in schea-service
            return (Objects.equals(result, "SUCCESS"));
        } else {
            return false;
        }
    }

    public static <T> boolean isResponseSuccessful(T responseType, Class<T> tClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (responseType == null) {
            return false;
        }
        var getStatusMethod = tClass.getMethod("getStatus");
        var result = (String) getStatusMethod.invoke(responseType).toString();
        // Add is successful to base in schema-service
        return (Objects.equals(result, "SUCCESS"));

    }

}
