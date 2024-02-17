package com.rtecsoft.alpha.dataservice.web.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSchemaResponse;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@SuperBuilder
public abstract class ResponseBase implements Serializable {
    public enum StatusEnum {
        SUCCESS("SUCCESS"),

        FAILURE("FAILURE");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static ResponseBase.StatusEnum fromValue(String value) {
            for (ResponseBase.StatusEnum b : ResponseBase.StatusEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }

        public boolean getSuccess() {
            return this == StatusEnum.SUCCESS;
        }
    }

    protected StatusEnum status;
    @Nullable
    protected String message;
}
