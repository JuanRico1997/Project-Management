package com.riwi.projectmanagement.domain.ports.in;

import java.util.UUID;

/**
 * Command - Datos de entrada para crear una tarea
 */
public class CreateTaskCommand {
    private final UUID projectId;
    private final String title;

    public CreateTaskCommand(UUID projectId, String title) {
        if (projectId == null) {
            throw new IllegalArgumentException("El ID del proyecto es obligatorio");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la tarea es obligatorio");
        }
        this.projectId = projectId;
        this.title = title.trim();
    }

    public UUID getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }
}
