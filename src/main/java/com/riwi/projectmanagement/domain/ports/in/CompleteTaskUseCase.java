package com.riwi.projectmanagement.domain.ports.in;

import com.riwi.projectmanagement.domain.models.Task;

/**
 * Puerto IN - Define el caso de uso "Completar Tarea"
 */
public interface CompleteTaskUseCase {
    /**
     * Marca una tarea como completada
     * Reglas:
     * - La tarea no debe estar ya completada
     * - Solo el propietario del proyecto puede completarla
     * - Genera auditoría y notificación
     *
     * @param command Datos de la tarea a completar
     * @return La tarea completada
     */
    Task execute(CompleteTaskCommand command);
}