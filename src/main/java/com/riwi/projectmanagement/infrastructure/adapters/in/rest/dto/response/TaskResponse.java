package com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.response;

import com.riwi.projectmanagement.domain.models.Task;
import java.util.UUID;

/**
 * DTO - Response de una tarea
 */
public class TaskResponse {
    private UUID id;
    private UUID projectId;
    private String title;
    private boolean completed;
    private boolean deleted;

    // Constructor vacío
    public TaskResponse() {
    }

    // Constructor completo
    public TaskResponse(UUID id, UUID projectId, String title,
                        boolean completed, boolean deleted) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.completed = completed;
        this.deleted = deleted;
    }

    /**
     * Convierte de Modelo de Dominio a DTO Response
     */
    public static TaskResponse fromDomain(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getProjectId(),
                task.getTitle(),
                task.isCompleted(),
                task.isDeleted()
        );
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
