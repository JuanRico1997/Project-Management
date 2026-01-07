package com.riwi.projectmanagement.infrastructure.adapters.out.persistence.repositories;

import com.riwi.projectmanagement.infrastructure.adapters.out.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio JPA de Spring Data para usuarios
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Busca un usuario por username
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Busca un usuario por email
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Verifica si existe un username
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un email
     */
    boolean existsByEmail(String email);
}
