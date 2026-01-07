package com.riwi.projectmanagement.domain.ports.out;

/**
 * Puerto OUT - Define la necesidad de enviar notificaciones
 */
public interface NotificationPort {
    /**
     * Envía una notificación
     * @param message Mensaje a enviar
     */
    void notify(String message);
}