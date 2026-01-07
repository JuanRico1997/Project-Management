package com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO - Request para crear un proyecto
 * Representa el JSON que envía el cliente
 */
public class CreateProjectRequest {

    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(min = 3, max = 255, message = "El nombre debe tener entre 3 y 255 caracteres")
    private String name;

    // Constructor vacío (necesario para Jackson)
    public CreateProjectRequest() {
    }

    public CreateProjectRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}