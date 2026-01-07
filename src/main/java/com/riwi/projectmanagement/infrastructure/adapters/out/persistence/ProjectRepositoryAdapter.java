package com.riwi.projectmanagement.infrastructure.adapters.out.persistence;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.ports.out.ProjectRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adaptador - Implementa el puerto OUT usando JPA
 * Es el "traductor" entre el dominio y JPA
 */
@Repository
public class ProjectRepositoryAdapter implements ProjectRepositoryPort {

    private final ProjectJpaRepository jpaRepository;

    public ProjectRepositoryAdapter(ProjectJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Project save(Project project) {
        // 1. Convertir de Dominio a JPA Entity
        ProjectEntity entity = ProjectEntity.fromDomain(project);

        // 2. Guardar usando JPA
        ProjectEntity savedEntity = jpaRepository.save(entity);

        // 3. Convertir de JPA Entity a Dominio
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Project> findById(UUID id) {
        // 1. Buscar en BD usando JPA
        Optional<ProjectEntity> entityOptional = jpaRepository.findById(id);

        // 2. Convertir a dominio si existe
        return entityOptional.map(ProjectEntity::toDomain);
    }

    @Override
    public List<Project> findByOwnerId(UUID ownerId) {
        // 1. Buscar en BD usando JPA
        List<ProjectEntity> entities = jpaRepository.findByOwnerIdAndDeletedFalse(ownerId);

        // 2. Convertir todos a dominio
        return entities.stream()
                .map(ProjectEntity::toDomain)
                .collect(Collectors.toList());
    }
}