package com.riwi.projectmanagement.infrastructure.adapters.out.persistence;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.models.ProjectStatus;
import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entidad JPA - Representa la tabla 'projects' en MySQL
 * ESTA clase SÍ conoce JPA
 */
@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "owner_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID ownerId;

    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status;

    @Column(nullable = false)
    private boolean deleted;

    // Constructor vacío (JPA lo requiere)
    public ProjectEntity() {
    }

    // Constructor completo
    public ProjectEntity(UUID id, UUID ownerId, String name,
                         ProjectStatus status, boolean deleted) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.status = status;
        this.deleted = deleted;
    }

    // ============================================
    // MÉTODOS DE CONVERSIÓN (MUY IMPORTANTES)
    // ============================================

    /**
     * Convierte de Entidad JPA a Modelo de Dominio
     * ProjectEntity → Project
     */
    public Project toDomain() {
        return new Project(
                this.id,
                this.ownerId,
                this.name,
                this.status,
                this.deleted
        );
    }

    /**
     * Convierte de Modelo de Dominio a Entidad JPA
     * Project → ProjectEntity
     */
    public static ProjectEntity fromDomain(Project project) {
        return new ProjectEntity(
                project.getId(),
                project.getOwnerId(),
                project.getName(),
                project.getStatus(),
                project.isDeleted()
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