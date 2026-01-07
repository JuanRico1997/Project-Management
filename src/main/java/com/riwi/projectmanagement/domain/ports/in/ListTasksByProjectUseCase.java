package com.riwi.projectmanagement.domain.ports.in;

import com.riwi.projectmanagement.domain.models.Task;
import java.util.List;

/**
 * Puerto IN - Define el caso de uso "Listar Tareas de un Proyecto"
 */
public interface ListTasksByProjectUseCase {
    /**
     * Lista todas las tareas de un proyecto (no eliminadas)
     * @param query Datos de consulta (projectId)
     * @return Lista de tareas del proyecto
     */
    List<Task> execute(ListTasksByProjectQuery query);
}