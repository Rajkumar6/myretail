package com.deepan.target.myretail.controller;

import com.deepan.target.myretail.entity.ErrorResponse;
import com.deepan.target.myretail.exception.ExternalSystemException;
import com.deepan.target.myretail.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.deepan.target.myretail.entity.ErrorCode.UNKNOWN_ERROR;
import static com.deepan.target.myretail.entity.ErrorCode.VALIDATION_ERROR;
import static com.deepan.target.myretail.entity.ErrorCode.PARSE_ERROR;

/**
 * Created by Deepan Sathyamoorthy
 * on 3/1/16.
 */
@ControllerAdvice
@RestController
public class ErrorHandler {

    private static final String PARSE_ERROR_MSG = "Failed to parse the JSON in the request body. ";

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse processValidationException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError fieldError = result.getFieldError();
        return new ErrorResponse(VALIDATION_ERROR.toString(), fieldError.getField() + " " + fieldError.getDefaultMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse processInvalidRequestBody(HttpMessageNotReadableException e) {
        String errorMessage = "";
        if(e.getRootCause() != null) {
            errorMessage = e.getRootCause().getMessage();
            errorMessage = errorMessage.contains("\n at") ? errorMessage.substring(0, errorMessage.indexOf("\n at")) : errorMessage;
        }
        return new ErrorResponse(PARSE_ERROR.toString(), PARSE_ERROR_MSG + errorMessage);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemNotFoundException.class)
    public ErrorResponse processItemNotFound(ItemNotFoundException e) {
        return new ErrorResponse(e.getErrorCode().toString(), e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ExternalSystemException.class)
    public ErrorResponse processExternalSystemError(ExternalSystemException e) {
        return new ErrorResponse(e.getErrorCode().toString(), e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse processException(Exception e) {
        return new ErrorResponse(UNKNOWN_ERROR.toString(), e.getMessage());
    }
}
