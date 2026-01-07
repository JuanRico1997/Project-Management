package com.riwi.projectmanagement.domain.ports.in;

import java.util.UUID;

/**
 * Command - Datos para completar una tarea
 */
public class CompleteTaskCommand {
    private final UUID taskId;

    public CompleteTaskCommand(UUID taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("El ID de la tarea es obligatorio");
        }
        this.taskId = taskId;
    }

    public UUID getTaskId() {
        return taskId;
    }
}