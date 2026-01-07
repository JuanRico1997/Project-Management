package com.riwi.projectmanagement.domain.ports.in;

import java.util.UUID;

/**
 * Command - Datos para eliminar un proyecto
 */
public class DeleteProjectCommand {
    private final UUID projectId;

    public DeleteProjectCommand(UUID projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("El ID del proyecto es obligatorio");
        }
        this.projectId = projectId;
    }

    public UUID getProjectId() {
        return projectId;
    }
}