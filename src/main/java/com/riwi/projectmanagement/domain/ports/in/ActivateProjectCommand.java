package com.riwi.projectmanagement.domain.ports.in;

import java.util.UUID;

/**
 * Command - Datos para activar un proyecto
 */
public class ActivateProjectCommand {
    private final UUID projectId;

    public ActivateProjectCommand(UUID projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("El ID del proyecto es obligatorio");
        }
        this.projectId = projectId;
    }

    public UUID getProjectId() {
        return projectId;
    }
}
