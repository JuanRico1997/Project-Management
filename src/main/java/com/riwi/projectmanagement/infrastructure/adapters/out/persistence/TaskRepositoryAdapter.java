package com.riwi.projectmanagement.infrastructure.adapters.out.persistence;

import com.riwi.projectmanagement.domain.models.Task;
import com.riwi.projectmanagement.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adaptador - Implementa el puerto OUT usando JPA
 */
@Repository
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskJpaRepository jpaRepository;

    public TaskRepositoryAdapter(TaskJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = TaskEntity.fromDomain(task);
        TaskEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Task> findById(UUID id) {
        Optional<TaskEntity> entityOptional = jpaRepository.findById(id);
        return entityOptional.map(TaskEntity::toDomain);
    }

    @Override
    public List<Task> findByProjectId(UUID projectId) {
        List<TaskEntity> entities = jpaRepository.findByProjectIdAndDeletedFalse(projectId);
        return entities.stream()
                .map(TaskEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int countActiveTasksByProjectId(UUID projectId) {
        return jpaRepository.countByProjectIdAndDeletedFalse(projectId);
    }
}