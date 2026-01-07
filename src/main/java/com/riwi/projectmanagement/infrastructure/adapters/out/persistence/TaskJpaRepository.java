package com.riwi.projectmanagement.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

/**
 * Repositorio JPA de Spring Data para tareas
 */
public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {

    /**
     * Busca tareas por proyecto que NO estén eliminadas
     */
    List<TaskEntity> findByProjectIdAndDeletedFalse(UUID projectId);

    /**
     * Cuenta tareas activas (no eliminadas) de un proyecto
     */
    int countByProjectIdAndDeletedFalse(UUID projectId);
}