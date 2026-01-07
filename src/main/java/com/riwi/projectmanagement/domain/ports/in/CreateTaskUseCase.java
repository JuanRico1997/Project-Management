package com.riwi.projectmanagement.domain.ports.in;

import com.riwi.projectmanagement.domain.models.Task;

/**
 * Puerto IN - Define el caso de uso "Crear Tarea"
 */
public interface CreateTaskUseCase {
    /**
     * Crea una nueva tarea en un proyecto
     * @param command Datos de la tarea a crear
     * @return La tarea creada
     */
    Task execute(CreateTaskCommand command);
}
