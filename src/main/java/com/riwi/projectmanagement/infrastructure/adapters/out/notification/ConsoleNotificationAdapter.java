package com.riwi.projectmanagement.infrastructure.adapters.out.notification;

import com.riwi.projectmanagement.domain.ports.out.NotificationPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Adaptador - Implementa notificaciones mostrando en consola
 * En producción, esto enviaría emails, SMS, push notifications, etc.
 */
@Component
public class ConsoleNotificationAdapter implements NotificationPort {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void notify(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String notification = String.format(
                "[NOTIFICATION] %s | %s",
                timestamp,
                message
        );

        System.out.println(notification);

        // TODO: En producción, enviar email/SMS:
        // emailService.send(userEmail, "Notificación", message);
    }
}