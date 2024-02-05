package com.rtecsoft.alpha.confluentschemaregistrydemo.web;

import com.rtecsoft.alpha.confluentschemaregistrydemo.web.resources.*;
import com.rtecsoft.alpha.confluentschemaregistrydemo.services.schema.SchemaRegistryService;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/schemas")
@Tags({@Tag( name="schema-service")})
public class SchemaServiceController {
    private final SchemaRegistryService schemaRegistryService;

    @RequestMapping(
            value = "schema/register",
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
            value = "schema/ids/{id}",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<GetSchemaResponse> getSchemaById(@PathVariable int id, @RequestParam(required = false) String subject) {
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
            value = "schema/{subject}",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<GetSchemaResponse> getSchemaBySubject(@PathVariable(required = true) String subject) {
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
            value = "schema/{subject}/{version}",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<GetSchemaResponse> getSchemaBySubjectAndVersion(@PathVariable("subject") String subject,
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

    @RequestMapping(
            value = "/subjects",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<GetSubjectsResponse> getSubjects() {
        try {
            var subjects = schemaRegistryService.getSubjects();
            var response = GetSubjectsResponse.builder()
                    .status(ResponseBase.Status.SUCCESS)
                    .subjects(subjects)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var response = GetSubjectsResponse.builder()
                    .status(ResponseBase.Status.FAILURE)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @RequestMapping(
            value = "/schema/versions/{subject}",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<GetSchemaVersionsResponse> getSchemaVersionsBySubject(@PathVariable("subject") String subject) {
        try {
            var versions = schemaRegistryService.getSchemaVersions(subject);
            var response = GetSchemaVersionsResponse.builder()
                    .status(ResponseBase.Status.SUCCESS)
                    .schemaVersions(versions)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var response = GetSchemaVersionsResponse.builder()
                    .status(ResponseBase.Status.FAILURE)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.ok(response);
        }
    }
}
