package com.riwi.projectmanagement.domain.ports.out;
import java.util.UUID;

/**
 * Puerto OUT - Obtiene el usuario autenticado actual
 */
public interface CurrentUserPort {
    /**
     * Obtiene el ID del usuario autenticado
     * @return UUID del usuario actual
     */
    UUID getCurrentUserId();
}
