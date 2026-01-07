package com.riwi.projectmanagement.infrastructure.adapters.in.rest.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO - Respuesta de error de validación (con detalles de campos)
 */
public class ValidationErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(int status, String message,
                                   Map<String, String> errors,
                                   LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    // Getters y Setters

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
