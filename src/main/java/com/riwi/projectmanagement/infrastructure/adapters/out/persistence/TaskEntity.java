package com.riwi.projectmanagement.infrastructure.adapters.out.persistence;

import com.riwi.projectmanagement.domain.models.Task;
import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entidad JPA - Representa la tabla 'tasks' en MySQL
 */
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "project_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID projectId;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private boolean deleted;

    // Constructor vacío (JPA lo requiere)
    public TaskEntity() {
    }

    // Constructor completo
    public TaskEntity(UUID id, UUID projectId, String title,
                      boolean completed, boolean deleted) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.completed = completed;
        this.deleted = deleted;
    }

    // ============================================
    // MÉTODOS DE CONVERSIÓN
    // ============================================

    /**
     * Convierte de Entidad JPA a Modelo de Dominio
     */
    public Task toDomain() {
        return new Task(
                this.id,
                this.projectId,
                this.title,
                this.completed,
                this.deleted
        );
    }

    /**
     * Convierte de Modelo de Dominio a Entidad JPA
     */
    public static TaskEntity fromDomain(Task task) {
        return new TaskEntity(
                task.getId(),
                task.getProjectId(),
                task.getTitle(),
                task.isCompleted(),
                task.isDeleted()
        );
    }

    // ============================================
    // GETTERS Y SETTERS
    // ============================================

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