package com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO - Request para crear una tarea
 */
public class CreateTaskRequest {

    @NotBlank(message = "El título de la tarea es obligatorio")
    @Size(min = 3, max = 500, message = "El título debe tener entre 3 y 500 caracteres")
    private String title;

    // Constructor vacío
    public CreateTaskRequest() {
    }

    public CreateTaskRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
