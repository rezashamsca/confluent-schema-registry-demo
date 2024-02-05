package com.rtecsoft.alpha.dataservice.services.schema;

import com.rtecsoft.alpha.dataservice.util.DataUtil;
import com.rtecsoft.alpha.openapi.schemaservice.model.GetSchemaResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class SchemaServiceImplTest {
    @Test
    public void isResponseSuccessful200SuccessTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        GetSchemaResponse getSchemaResponse = new GetSchemaResponse();
        getSchemaResponse.setStatus(GetSchemaResponse.StatusEnum.SUCCESS);
        getSchemaResponse.setSchema("{\"testSchema\":\"object\"}");
        getSchemaResponse.setMessage("");
        ResponseEntity<GetSchemaResponse> responseEntity = ResponseEntity.ok(getSchemaResponse);
        boolean expected = DataUtil.isResponseSuccessful(responseEntity, GetSchemaResponse.class);
        assertTrue(expected);
    }

    @Test
    public void isResponseSuccessful200FailureTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        GetSchemaResponse getSchemaResponse = new GetSchemaResponse();
        getSchemaResponse.setStatus(GetSchemaResponse.StatusEnum.FAILURE);
        getSchemaResponse.setSchema("");
        getSchemaResponse.setMessage("Exception happened!");
        ResponseEntity<GetSchemaResponse> responseEntity = ResponseEntity.ok(getSchemaResponse);
        boolean expected = DataUtil.isResponseSuccessful(responseEntity, GetSchemaResponse.class);
        assertFalse(expected);
    }

}