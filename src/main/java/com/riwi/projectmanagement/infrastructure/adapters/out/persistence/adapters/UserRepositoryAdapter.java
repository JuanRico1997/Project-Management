package com.riwi.projectmanagement.infrastructure.adapters.out.persistence.adapters;

import com.riwi.projectmanagement.domain.models.User;
import com.riwi.projectmanagement.domain.ports.out.UserRepositoryPort;
import com.riwi.projectmanagement.infrastructure.adapters.out.persistence.entities.UserEntity;
import com.riwi.projectmanagement.infrastructure.adapters.out.persistence.repositories.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador - Implementa el puerto OUT usando JPA
 */
@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<User> findById(UUID id) {
        Optional<UserEntity> entityOptional = jpaRepository.findById(id);
        return entityOptional.map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> entityOptional = jpaRepository.findByUsername(username);
        return entityOptional.map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> entityOptional = jpaRepository.findByEmail(email);
        return entityOptional.map(UserEntity::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
