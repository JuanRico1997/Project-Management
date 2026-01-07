package com.riwi.projectmanagement.domain.ports.out;


import com.riwi.projectmanagement.domain.models.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto OUT - Define lo que el dominio necesita de persistencia para tareas
 */
public interface TaskRepositoryPort {

    /**
     * Guarda una tarea (nueva o existente)
     */
    Task save(Task task);

    /**
     * Busca una tarea por ID
     */
    Optional<Task> findById(UUID id);

    /**
     * Lista todas las tareas de un proyecto (no eliminadas)
     */
    List<Task> findByProjectId(UUID projectId);

    /**
     * Cuenta las tareas activas (no eliminadas) de un proyecto
     */
    int countActiveTasksByProjectId(UUID projectId);
}
