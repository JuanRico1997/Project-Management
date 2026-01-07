package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.ports.in.ActivateProjectCommand;
import com.riwi.projectmanagement.domain.ports.in.ActivateProjectUseCase;
import com.riwi.projectmanagement.domain.ports.out.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service - Implementa el caso de uso "Activar Proyecto"
 */
@Service
public class ActivateProjectService implements ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final TaskRepositoryPort taskRepository;
    private final CurrentUserPort currentUserPort;
    private final AuditLogPort auditLogPort;
    private final NotificationPort notificationPort;

    public ActivateProjectService(
            ProjectRepositoryPort projectRepository,
            TaskRepositoryPort taskRepository,
            CurrentUserPort currentUserPort,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.currentUserPort = currentUserPort;
        this.auditLogPort = auditLogPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public Project execute(ActivateProjectCommand command) {
        // 1. Obtener el usuario autenticado
        UUID currentUserId = currentUserPort.getCurrentUserId();

        // 2. Buscar el proyecto
        Project project = projectRepository.findById(command.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Proyecto no encontrado: " + command.getProjectId()
                ));

        // 3. Verificar que el usuario es el propietario (Regla de negocio)
        if (!project.isOwnedBy(currentUserId)) {
            throw new IllegalStateException(
                    "Solo el propietario puede activar el proyecto"
            );
        }

        // 4. Contar las tareas activas del proyecto
        int activeTasksCount = taskRepository.countActiveTasksByProjectId(project.getId());

        // 5. Activar el proyecto (aplica regla de negocio: min 1 tarea)
        // El método activate() del dominio valida la regla
        project.activate(activeTasksCount);

        // 6. Guardar el proyecto actualizado
        Project activatedProject = projectRepository.save(project);

        // 7. Registrar auditoría
        auditLogPort.register("PROJECT_ACTIVATED", activatedProject.getId());

        // 8. Enviar notificación
        notificationPort.notify(
                "Proyecto '" + activatedProject.getName() + "' ha sido activado"
        );

        // 9. Devolver el proyecto activado
        return activatedProject;
    }
}
