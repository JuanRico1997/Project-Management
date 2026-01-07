package com.riwi.projectmanagement.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

/**
 * Repositorio JPA de Spring Data
 * Spring implementa automáticamente los métodos
 */
public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, UUID> {

    /**
     * Busca proyectos por propietario que NO estén eliminados
     * Spring genera automáticamente la query
     */
    List<ProjectEntity> findByOwnerIdAndDeletedFalse(UUID ownerId);
}
