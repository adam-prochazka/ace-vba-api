package com.acevba.springapi.exception;

import java.util.Date;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Tag(name = "Error Handling"/*, description = "Controller for handling errors."*/)
public class ControllerExceptionHandler {

    @Operation(summary = "Handle ResourceNotFoundException", tags = { "Error Handling" }, responses = {
            @ApiResponse(responseCode = "404", description = "Resource not found.")
    })
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                "[NOT-FOUND]: " + ex.getMessage(),
                request.getDescription(false));
    }

    @Operation(summary = "Handle Exception", tags = { "Error Handling" }, responses = {
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                "[ERROR]: " + ex.getMessage(),
                request.getDescription(false));
    }
}