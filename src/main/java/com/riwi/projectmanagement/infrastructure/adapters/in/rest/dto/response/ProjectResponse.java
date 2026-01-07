package com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.response;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.models.ProjectStatus;

import java.util.UUID;

/**
 * DTO - Response de un proyecto
 * Representa el JSON que devolvemos al cliente
 */
public class ProjectResponse {
    private UUID id;
    private UUID ownerId;
    private String name;
    private ProjectStatus status;
    private boolean deleted;

    // Constructor vacío
    public ProjectResponse() {
    }

    // Constructor completo
    public ProjectResponse(UUID id, UUID ownerId, String name,
                           ProjectStatus status, boolean deleted) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.status = status;
        this.deleted = deleted;
    }

    /**
     * Convierte de Modelo de Dominio a DTO Response
     * Project → ProjectResponse
     */
    public static ProjectResponse fromDomain(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getOwnerId(),
                project.getName(),
                project.getStatus(),
                project.isDeleted()
        );
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
