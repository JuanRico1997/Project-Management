package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.models.Task;
import com.riwi.projectmanagement.domain.ports.in.CreateTaskCommand;
import com.riwi.projectmanagement.domain.ports.in.CreateTaskUseCase;
import com.riwi.projectmanagement.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanagement.domain.ports.out.ProjectRepositoryPort;
import com.riwi.projectmanagement.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service - Implementa el caso de uso "Crear Tarea"
 */
@Service
public class CreateTaskService implements CreateTaskUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public CreateTaskService(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public Task execute(CreateTaskCommand command) {
        // 1. Obtener el usuario autenticado
        UUID currentUserId = currentUserPort.getCurrentUserId();

        // 2. Verificar que el proyecto existe
        Project project = projectRepository.findById(command.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Proyecto no encontrado: " + command.getProjectId()
                ));

        // 3. Verificar que el usuario es el propietario del proyecto
        if (!project.isOwnedBy(currentUserId)) {
            throw new IllegalStateException(
                    "No tienes permiso para agregar tareas a este proyecto"
            );
        }

        // 4. Verificar que el proyecto no esté eliminado
        if (project.isDeleted()) {
            throw new IllegalStateException(
                    "No se pueden agregar tareas a un proyecto eliminado"
            );
        }

        // 5. Crear la tarea
        Task newTask = new Task(command.getProjectId(), command.getTitle());

        // 6. Guardar la tarea
        Task savedTask = taskRepository.save(newTask);

        // 7. Devolver la tarea creada
        return savedTask;
    }
}
