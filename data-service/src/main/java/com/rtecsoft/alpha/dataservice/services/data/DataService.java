package com.rtecsoft.alpha.dataservice.services.data;

import com.rtecsoft.alpha.dataservice.domain.DataEntities;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DataService {
    private final DataRepository dataRepository;

    public DataEntities insertData(DataEntities data) {
        return dataRepository.insert(data);
    }

    public DataEntities getData(String id) {
        return dataRepository.findById(id).orElse(null);
    }

    public void deleteData(String id) {
        dataRepository.deleteById(id);
    }

    public DataEntities updateData(DataEntities data) {
        return dataRepository.save(data);
    }
}
