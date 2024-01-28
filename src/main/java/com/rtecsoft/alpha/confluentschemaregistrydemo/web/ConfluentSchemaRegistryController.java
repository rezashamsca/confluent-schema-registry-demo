package com.rtecsoft.alpha.confluentschemaregistrydemo.web;

import com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources.GetSchemaResponse;
import com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources.RegisterRequest;
import com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources.RegisterResponse;
import com.rtecsoft.alpha.confluentschemaregistrydemo.services.schema.SchemaRegistryService;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/schemas")
public class ConfluentSchemaRegistryController {
    private final SchemaRegistryService schemaRegistryService;

    @RequestMapping(
            value = "/register",
            consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity<RegisterResponse> registerJsonSchema(@RequestBody RegisterRequest request) {
        try {
            int schemaId =  schemaRegistryService.registerJsonSchema(request.getSubject(), request.jsonSchemaToString());
            var response = RegisterResponse.builder()
                    .schemaId(schemaId)
                    .status(RegisterResponse.Status.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok(RegisterResponse.builder().status(RegisterResponse.Status.FAILURE).build());
        }
    }

    @RequestMapping(
            value = "/ids/{id}",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<GetSchemaResponse> getSchema(@PathVariable int id, @RequestParam(required = false) String subject) {
        try {
            ParsedSchema schema = schemaRegistryService.getSchema((subject == null) ? "": subject, id);
            var response = GetSchemaResponse.builder()
                    .schema(schema.canonicalString())
                    .status(GetSchemaResponse.Status.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(
            value = "/",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<GetSchemaResponse> getSchema(@RequestParam(required = true) String subject) {
        try {
            SchemaMetadata schemaMetadata = schemaRegistryService.getSchema(subject);
            var response = GetSchemaResponse.builder()
                    .schema(schemaMetadata.getSchema())
                    .status(GetSchemaResponse.Status.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(
            value = "/schemas/{subject}/{version}",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<GetSchemaResponse> getSchema(@PathVariable("subject") String subject,
                                                       @PathVariable("version") int version) {
        try {
            String schema = schemaRegistryService.getSchemaBySubjectAndVersion(subject, version);
            var response = GetSchemaResponse.builder()
                    .schema(schema)
                    .status(GetSchemaResponse.Status.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
