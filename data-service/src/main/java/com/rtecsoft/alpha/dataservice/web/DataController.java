package com.rtecsoft.alpha.dataservice.web;

import com.rtecsoft.alpha.dataservice.domain.DataEntities;
import com.rtecsoft.alpha.dataservice.services.data.DataService;
import com.rtecsoft.alpha.dataservice.services.schema.JsonSchemaValidator;
import com.rtecsoft.alpha.dataservice.services.schema.SchemaServiceImpl;
import com.rtecsoft.alpha.dataservice.util.DataUtil;
import com.rtecsoft.alpha.dataservice.web.resources.DataRequest;
import com.rtecsoft.alpha.dataservice.web.resources.DataResponse;
import com.rtecsoft.alpha.dataservice.web.resources.GetDataResponse;
import com.rtecsoft.alpha.dataservice.web.resources.ResponseBase;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSubjectsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/data")
public class DataController {
    private final SchemaServiceImpl schemaService;
    private final DataService dataService;

    @RequestMapping(value = "/action",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<DataResponse> dataAction(@RequestBody DataRequest dataRequest) throws Exception {

        try {
            // Make sure data request subject is valid
            var subjects = schemaService.getSubjects();
            if (!DataUtil.isResponseSuccessful(subjects, GetSubjectsResponse.class)) {
                log.info("GET subjects not successful");
                return ResponseEntity.notFound().build();
            }
            else {
                log.debug("GET subjects successful: {}", subjects);
            }

            var subject = Objects.requireNonNull(subjects.getSubjects()).stream()
                    .filter(s -> s.equals(dataRequest.getSchemaSubject()))
                    .findFirst()
                    .orElse(null);
            if (subject == null) {
                log.info("Schema subject not found: " + dataRequest.getSchemaSubject());
                return ResponseEntity.notFound().build();
            }

            if (DataUtil.isVersionIfEmpty(dataRequest)) {
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
                        .status(ResponseBase.StatusEnum.SUCCESS)
                        .objectId(response.get_id())
                        .build());
            } else {
                // Return response
                return ResponseEntity.ok(DataResponse.builder()
                        .status(ResponseBase.StatusEnum.SUCCESS)
                        .build());
            }
        } catch (Exception e) {
            log.error("Error processing data request: " + e.getMessage());
            return ResponseEntity.internalServerError().body(DataResponse.builder()
                    .status(ResponseBase.StatusEnum.FAILURE)
                    .message(e.getMessage())
                    .objectId("")
                    .build());
        }
    }

    private Integer getLastSchemaVersion(String subject) throws Exception {
        var response = this.schemaService.getSchemaVersions(subject);
        var allVersions = Objects.requireNonNull(response).getSchemaVersions();

        assert allVersions != null;
        return allVersions.get(allVersions.size() - 1);
    }


    private String getSchema(String subject, Integer version) throws Exception {
        return Objects.requireNonNull(schemaService.getSchemaBySubjectAndVersion(subject, version)).getSchema();
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

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<GetDataResponse> getData(@PathVariable("id") String id) {
        var data = dataService.getData(id);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(GetDataResponse.builder()
                .status(ResponseBase.StatusEnum.SUCCESS)
                .data(data)
                .build());
    }


}
