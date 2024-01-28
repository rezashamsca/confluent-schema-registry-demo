package com.rtecsoft.alpha.confluentschemaregistrydemo.services.schema;

import com.rtecsoft.alpha.confluentschemaregistrydemo.domain.DataEntities;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.MongoRepository;

@EnableMongoAuditing
public interface DataRepository extends MongoRepository<DataEntities, String> {
}
