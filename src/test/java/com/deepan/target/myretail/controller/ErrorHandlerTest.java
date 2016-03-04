package com.deepan.target.myretail.controller;

import com.deepan.target.myretail.entity.ErrorResponse;
import com.deepan.target.myretail.exception.ExternalSystemException;
import com.deepan.target.myretail.exception.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.deepan.target.myretail.entity.ErrorCode.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/3/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ErrorHandlerTest {

    private ErrorHandler target;
    @Mock
    private MethodParameter parameter;
    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        target = new ErrorHandler();
    }

    @Test
    public void testProcessValidationException() throws Exception {
        when(bindingResult.getFieldError()).thenReturn(new FieldError("productPriceConversation", "id", "may not be empty"));
        ErrorResponse result = target.processValidationException(new MethodArgumentNotValidException(parameter, bindingResult));
        assertNotNull(result);
        assertEquals(VALIDATION_ERROR.toString(), result.getErrorCode());
        assertEquals("id may not be empty", result.getErrorDescription());
    }

    @Test
    public void testProcessInvalidRequestBody() throws Exception {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Expecting ',' at index 120");
        ErrorResponse result = target.processInvalidRequestBody(exception);
        assertNotNull(result);
        assertEquals(PARSE_ERROR.toString(), result.getErrorCode());
        assertEquals("Failed to parse the JSON in the request body. ", result.getErrorDescription());
    }

    @Test
    public void testProcessItemNotFound() throws Exception {
        ErrorResponse result = target.processItemNotFound(new ItemNotFoundException("Product Not Found"));
        assertNotNull(result);
        assertEquals(ITEM_NOT_FOUND.toString(), result.getErrorCode());
        assertEquals("Product Not Found", result.getErrorDescription());
    }

    @Test
    public void testProcessExternalSystemError() throws Exception {
        ErrorResponse result = target.processExternalSystemError(new ExternalSystemException("Error connecting to DB"));
        assertNotNull(result);
        assertEquals(EXT_SYS_ERROR.toString(), result.getErrorCode());
        assertEquals("Error connecting to DB", result.getErrorDescription());
    }

    @Test
    public void testProcessException() throws Exception {
        ErrorResponse result = target.processException(new Exception("Message failed"));
        assertNotNull(result);
        assertEquals(UNKNOWN_ERROR.toString(), result.getErrorCode());
        assertEquals("Message failed", result.getErrorDescription());
    }
}