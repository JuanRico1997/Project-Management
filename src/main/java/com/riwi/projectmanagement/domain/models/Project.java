package com.riwi.projectmanagement.domain.models;

import java.util.UUID;

/**
 * Modelo de dominio PURO - Representa un Proyecto
 * NO depende de JPA, Spring ni ningún framework
 */
public class Project {
    private UUID id;
    private UUID ownerId;
    private String name;
    private ProjectStatus status;
    private boolean deleted;

    // Constructor para crear proyecto nuevo
    public Project(UUID ownerId, String name) {
        this.id = UUID.randomUUID();  // Genera UUID automáticamente
        this.ownerId = ownerId;
        this.name = name;
        this.status = ProjectStatus.DRAFT;  // Siempre empieza en DRAFT
        this.deleted = false;
    }

    // Constructor completo (para recrear desde BD)
    public Project(UUID id, UUID ownerId, String name,
                   ProjectStatus status, boolean deleted) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.status = status;
        this.deleted = deleted;
    }

    // ========================================
    // MÉTODOS DE NEGOCIO
    // ========================================

    /**
     * Activa el proyecto
     * Regla de negocio: Solo puede activarse si tiene al menos una tarea
     */
    public void activate(int taskCount) {
        if (this.deleted) {
            throw new IllegalStateException("No se puede activar un proyecto eliminado");
        }

        if (this.status == ProjectStatus.ACTIVE) {
            throw new IllegalStateException("El proyecto ya está activo");
        }

        if (taskCount == 0) {
            throw new IllegalStateException(
                    "El proyecto debe tener al menos una tarea para activarse"
            );
        }

        this.status = ProjectStatus.ACTIVE;
    }

    /**
     * Elimina lógicamente el proyecto (soft delete)
     */
    public void delete() {
        if (this.deleted) {
            throw new IllegalStateException("El proyecto ya está eliminado");
        }
        this.deleted = true;
    }

    /**
     * Verifica si el usuario es el propietario
     */
    public boolean isOwnedBy(UUID userId) {
        return this.ownerId.equals(userId);
    }

    // ========================================
    // GETTERS (NO hay setters para mantener inmutabilidad)
    // ========================================

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public boolean isDeleted() {
        return deleted;
    }
}