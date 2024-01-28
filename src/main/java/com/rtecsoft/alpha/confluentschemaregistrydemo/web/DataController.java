package com.rtecsoft.alpha.confluentschemaregistrydemo.web;

import com.rtecsoft.alpha.confluentschemaregistrydemo.domain.DataEntities;
import com.rtecsoft.alpha.confluentschemaregistrydemo.services.schema.DataService;
import com.rtecsoft.alpha.confluentschemaregistrydemo.services.schema.JsonSchemaValidator;
import com.rtecsoft.alpha.confluentschemaregistrydemo.services.schema.SchemaRegistryServiceImpl;
import com.rtecsoft.alpha.confluentschemaregistrydemo.util.DataUtil;
import com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources.DataRequest;
import com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources.DataResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/data")
public class DataController {
    private final SchemaRegistryServiceImpl schemaRegistryService;
    private final DataService dataService;

    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<DataResponse> data(@RequestBody DataRequest dataRequest) throws Exception {

        try {
            // Validate schema
            var subjects = schemaRegistryService.getSubjects();
            var subject = subjects.stream()
                    .filter(s -> s.equals(dataRequest.getSchemaSubject()))
                    .findFirst()
                    .orElse(null);
            if (subject == null) {
                log.info("Schema subject not found: " + dataRequest.getSchemaSubject());
                return ResponseEntity.notFound().build();
            }

            if (DataUtil.isVersionIfEmpty(dataRequest.getSchemaVersion())) {
                dataRequest.setSchemaVersion(getLastSchemaVersion(subject));
            }

            var schema = this.getSchema(subject, dataRequest.getSchemaVersion());

            // Validate data against schema
            try {
                JsonSchemaValidator.validateJson(dataRequest.payloadToString(), schema);
            } catch (Exception e) {
                log.info("Invalid data: " + e.getMessage());
                return ResponseEntity.badRequest().build();
            }

            // If all ok, process the action
            var response = processAction(dataRequest);

            if (DataUtil.isReturnable(dataRequest.getAction())) {
                assert response != null;
                return ResponseEntity.ok(DataResponse.builder()
                        .status(DataResponse.StatusEnum.SUCCESS)
                        .objectId(response.get_id())
                        .build());
            } else {
                // Return response
                return ResponseEntity.ok(DataResponse.builder()
                        .status(DataResponse.StatusEnum.SUCCESS)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(DataResponse.builder()
                    .status(DataResponse.StatusEnum.ERROR)
                    .message(e.getMessage())
                    .objectId("")
                    .build());
        }
    }

    private Integer getLastSchemaVersion(String subject) throws Exception {
        var allVersions = schemaRegistryService.getSchemaVersions(subject);
        return allVersions.get(allVersions.size() - 1);
    }


    private String getSchema(String subject, Integer version) throws Exception {
        return schemaRegistryService.getSchemaBySubjectAndVersion(subject, version);
    }

    private DataEntities processAction(DataRequest dataRequest) {
        var data = DataUtil.map(dataRequest);
        switch (dataRequest.getAction()) {
            case "create" -> {
                return dataService.insertData(data);
            }
            case "update" -> {
                return dataService.updateData(data);
            }
            case "delete" -> {
                dataService.deleteData(dataRequest.getId());
                return null;
            }
            default -> throw new RuntimeException("Invalid action: " + dataRequest.getAction());
        }
    }

}
