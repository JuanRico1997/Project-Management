package com.riwi.projectmanagement.domain.ports.out;

import com.riwi.projectmanagement.domain.models.Project;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto OUT - Define lo que el dominio necesita de persistencia
 */
public interface ProjectRepositoryPort {

    /**
     * Guarda un proyecto (nuevo o existente)
     */
    Project save(Project project);

    /**
     * Busca un proyecto por ID
     */
    Optional<Project> findById(UUID id);

    /**
     * Lista todos los proyectos de un usuario (no eliminados)
     */
    List<Project> findByOwnerId(UUID ownerId);
}
