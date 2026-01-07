package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.ports.in.DeleteProjectCommand;
import com.riwi.projectmanagement.domain.ports.in.DeleteProjectUseCase;
import com.riwi.projectmanagement.domain.ports.out.AuditLogPort;
import com.riwi.projectmanagement.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanagement.domain.ports.out.NotificationPort;
import com.riwi.projectmanagement.domain.ports.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service - Implementa el caso de uso "Eliminar Proyecto"
 */
@Service
public class DeleteProjectService implements DeleteProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;
    private final AuditLogPort auditLogPort;
    private final NotificationPort notificationPort;

    public DeleteProjectService(
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort) {
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
        this.auditLogPort = auditLogPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public Project execute(DeleteProjectCommand command) {
        // 1. Obtener el usuario autenticado
        UUID currentUserId = currentUserPort.getCurrentUserId();

        // 2. Buscar el proyecto
        Project project = projectRepository.findById(command.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Proyecto no encontrado: " + command.getProjectId()
                ));

        // 3. Verificar que el usuario es el propietario
        if (!project.isOwnedBy(currentUserId)) {
            throw new IllegalStateException(
                    "Solo el propietario puede eliminar el proyecto"
            );
        }

        // 4. Eliminar el proyecto (soft delete)
        // El método delete() del dominio valida que no esté ya eliminado
        project.delete();

        // 5. Guardar el proyecto actualizado
        Project deletedProject = projectRepository.save(project);

        // 6. Registrar auditoría
        auditLogPort.register("PROJECT_DELETED", deletedProject.getId());

        // 7. Enviar notificación
        notificationPort.notify(
                "Proyecto '" + deletedProject.getName() + "' ha sido eliminado"
        );

        // 8. Devolver el proyecto eliminado
        return deletedProject;
    }
}
