package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.models.Task;
import com.riwi.projectmanagement.domain.ports.in.DeleteTaskCommand;
import com.riwi.projectmanagement.domain.ports.in.DeleteTaskUseCase;
import com.riwi.projectmanagement.domain.ports.out.AuditLogPort;
import com.riwi.projectmanagement.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanagement.domain.ports.out.NotificationPort;
import com.riwi.projectmanagement.domain.ports.out.ProjectRepositoryPort;
import com.riwi.projectmanagement.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service - Implementa el caso de uso "Eliminar Tarea"
 */
@Service
public class DeleteTaskService implements DeleteTaskUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;
    private final AuditLogPort auditLogPort;
    private final NotificationPort notificationPort;

    public DeleteTaskService(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
        this.auditLogPort = auditLogPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public Task execute(DeleteTaskCommand command) {
        // 1. Obtener el usuario autenticado
        UUID currentUserId = currentUserPort.getCurrentUserId();

        // 2. Buscar la tarea
        Task task = taskRepository.findById(command.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tarea no encontrada: " + command.getTaskId()
                ));

        // 3. Buscar el proyecto al que pertenece la tarea
        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Proyecto no encontrado: " + task.getProjectId()
                ));

        // 4. Verificar que el usuario es el propietario del proyecto
        if (!project.isOwnedBy(currentUserId)) {
            throw new IllegalStateException(
                    "Solo el propietario del proyecto puede eliminar tareas"
            );
        }

        // 5. Eliminar la tarea (soft delete)
        // El método delete() del dominio valida que no esté ya eliminada
        task.delete();

        // 6. Guardar la tarea actualizada
        Task deletedTask = taskRepository.save(task);

        // 7. Registrar auditoría
        auditLogPort.register("TASK_DELETED", deletedTask.getId());

        // 8. Enviar notificación
        notificationPort.notify(
                "Tarea '" + deletedTask.getTitle() + "' ha sido eliminada"
        );

        // 9. Devolver la tarea eliminada
        return deletedTask;
    }
}
