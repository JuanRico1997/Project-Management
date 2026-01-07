package com.riwi.projectmanagement.domain.ports.in;

import java.util.UUID;

/**
 * Query - Datos para listar tareas de un proyecto
 */
public class ListTasksByProjectQuery {
    private final UUID projectId;

    public ListTasksByProjectQuery(UUID projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("El ID del proyecto es obligatorio");
        }
        this.projectId = projectId;
    }

    public UUID getProjectId() {
        return projectId;
    }
}