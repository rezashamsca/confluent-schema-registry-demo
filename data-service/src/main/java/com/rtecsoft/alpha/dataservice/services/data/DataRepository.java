package com.rtecsoft.alpha.dataservice.services.data;

import com.rtecsoft.alpha.dataservice.domain.DataEntities;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.MongoRepository;

@EnableMongoAuditing
public interface DataRepository extends MongoRepository<DataEntities, String> {
}
