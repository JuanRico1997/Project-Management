package com.riwi.projectmanagement.domain.models;

import java.util.UUID;

/**
 * Modelo de dominio PURO - Representa una Tarea
 * NO depende de JPA, Spring ni ningún framework
 */
public class Task {
    private UUID id;
    private UUID projectId;
    private String title;
    private boolean completed;
    private boolean deleted;

    // Constructor para crear tarea nueva
    public Task(UUID projectId, String title) {
        this.id = UUID.randomUUID();  // Genera UUID automáticamente
        this.projectId = projectId;
        this.title = title;
        this.completed = false;  // Por defecto no completada
        this.deleted = false;
    }

    // Constructor completo (para recrear desde BD)
    public Task(UUID id, UUID projectId, String title,
                boolean completed, boolean deleted) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.completed = completed;
        this.deleted = deleted;
    }

    // ========================================
    // MÉTODOS DE NEGOCIO
    // ========================================

    /**
     * Marca la tarea como completada
     * Regla de negocio: Una tarea completada no puede modificarse
     */
    public void complete() {
        if (this.deleted) {
            throw new IllegalStateException("No se puede completar una tarea eliminada");
        }

        if (this.completed) {
            throw new IllegalStateException("La tarea ya está completada");
        }

        this.completed = true;
    }

    /**
     * Elimina lógicamente la tarea (soft delete)
     */
    public void delete() {
        if (this.deleted) {
            throw new IllegalStateException("La tarea ya está eliminada");
        }
        this.deleted = true;
    }

    /**
     * Verifica si la tarea pertenece a un proyecto específico
     */
    public boolean belongsToProject(UUID projectId) {
        return this.projectId.equals(projectId);
    }

    // ========================================
    // GETTERS (NO setters para mantener inmutabilidad)
    // ========================================

    public UUID getId() {
        return id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isDeleted() {
        return deleted;
    }
}