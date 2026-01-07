package com.riwi.projectmanagement.domain.ports.in;

import com.riwi.projectmanagement.domain.models.Task;

/**
 * Puerto IN - Define el caso de uso "Eliminar Tarea"
 */
public interface DeleteTaskUseCase {
    /**
     * Elimina lógicamente una tarea
     * Reglas:
     * - Solo el propietario del proyecto puede eliminarla
     * - No puede estar ya eliminada
     *
     * @param command Datos de la tarea a eliminar
     * @return La tarea eliminada
     */
    Task execute(DeleteTaskCommand command);
}