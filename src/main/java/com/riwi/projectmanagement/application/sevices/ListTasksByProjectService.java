package com.riwi.projectmanagement.application.sevices;

import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.models.Task;
import com.riwi.projectmanagement.domain.ports.in.ListTasksByProjectQuery;
import com.riwi.projectmanagement.domain.ports.in.ListTasksByProjectUseCase;
import com.riwi.projectmanagement.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanagement.domain.ports.out.ProjectRepositoryPort;
import com.riwi.projectmanagement.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service - Implementa el caso de uso "Listar Tareas de un Proyecto"
 */
@Service
public class ListTasksByProjectService implements ListTasksByProjectUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public ListTasksByProjectService(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public List<Task> execute(ListTasksByProjectQuery query) {
        // 1. Obtener el usuario autenticado
        UUID currentUserId = currentUserPort.getCurrentUserId();

        // 2. Verificar que el proyecto existe
        Project project = projectRepository.findById(query.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Proyecto no encontrado: " + query.getProjectId()
                ));

        // 3. Verificar que el usuario es el propietario del proyecto
        if (!project.isOwnedBy(currentUserId)) {
            throw new IllegalStateException(
                    "Solo el propietario puede ver las tareas del proyecto"
            );
        }

        // 4. Listar todas las tareas del proyecto (no eliminadas)
        List<Task> tasks = taskRepository.findByProjectId(query.getProjectId());

        // 5. Devolver la lista
        return tasks;
    }
}