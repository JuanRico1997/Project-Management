package com.riwi.projectmanagement.domain.ports.out;

import com.riwi.projectmanagement.domain.models.User;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto OUT - Define lo que el dominio necesita de persistencia para usuarios
 */
public interface UserRepositoryPort {

    /**
     * Guarda un usuario
     */
    User save(User user);

    /**
     * Busca un usuario por ID
     */
    Optional<User> findById(UUID id);

    /**
     * Busca un usuario por username
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por email
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica si existe un username
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un email
     */
    boolean existsByEmail(String email);
}