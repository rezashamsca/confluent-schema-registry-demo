package com.rtecsoft.alpha.confluentschemaregistrydemo.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Builder
@Document(collection = "entities")
public class DataEntities {
    @Id
    private String _id;
    @CreatedDate
    private Date createdDate;
    private String schemaSubject;
    private int schemaVersion;
    private String payload;
}
