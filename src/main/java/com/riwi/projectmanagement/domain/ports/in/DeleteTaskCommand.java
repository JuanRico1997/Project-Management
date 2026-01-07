package com.riwi.projectmanagement.domain.ports.in;

import java.util.UUID;

/**
 * Command - Datos para eliminar una tarea
 */
public class DeleteTaskCommand {
    private final UUID taskId;

    public DeleteTaskCommand(UUID taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("El ID de la tarea es obligatorio");
        }
        this.taskId = taskId;
    }

    public UUID getTaskId() {
        return taskId;
    }
}