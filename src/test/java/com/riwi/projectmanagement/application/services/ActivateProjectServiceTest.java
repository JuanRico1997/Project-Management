package com.riwi.projectmanagement.application.services;

import com.riwi.projectmanagement.application.sevices.ActivateProjectService;
import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.models.ProjectStatus;
import com.riwi.projectmanagement.domain.ports.in.ActivateProjectCommand;
import com.riwi.projectmanagement.domain.ports.out.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivateProjectServiceTest {

    @Mock
    private ProjectRepositoryPort projectRepository;
    @Mock
    private TaskRepositoryPort taskRepository;
    @Mock
    private CurrentUserPort currentUserPort;
    @Mock
    private AuditLogPort auditLogPort;
    @Mock
    private NotificationPort notificationPort;

    @InjectMocks
    private ActivateProjectService activateProjectService;

    @Test
    @DisplayName("Debe activar el proyecto cuando tiene tareas y el usuario es el dueño")
    void activateProject_WithTasks_ShouldSucceed() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        ActivateProjectCommand command = new ActivateProjectCommand(projectId);

        // Usamos tu constructor completo: Project(id, ownerId, name, status, deleted)
        Project project = new Project(projectId, userId, "Proyecto Test", ProjectStatus.DRAFT, false);

        when(currentUserPort.getCurrentUserId()).thenReturn(userId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.countActiveTasksByProjectId(projectId)).thenReturn(1);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Act
        Project result = activateProjectService.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(ProjectStatus.ACTIVE, result.getStatus());
        verify(projectRepository).save(any(Project.class));
        verify(auditLogPort).register(eq("PROJECT_ACTIVATED"), eq(projectId));
        verify(notificationPort).notify(anyString());
    }

    @Test
    @DisplayName("Debe fallar si el proyecto no tiene tareas")
    void activateProject_WithoutTasks_ShouldFail() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        ActivateProjectCommand command = new ActivateProjectCommand(projectId);
        Project project = new Project(projectId, userId, "Sin Tareas", ProjectStatus.DRAFT, false);

        when(currentUserPort.getCurrentUserId()).thenReturn(userId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.countActiveTasksByProjectId(projectId)).thenReturn(0);

        // Act & Assert
        // El modelo lanza IllegalStateException si taskCount == 0
        assertThrows(IllegalStateException.class, () -> activateProjectService.execute(command));
        verify(projectRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe fallar si el usuario no es el dueño")
    void activateProject_ByNonOwner_ShouldFail() {
        // Arrange
        UUID ownerId = UUID.randomUUID();
        UUID strangerId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        ActivateProjectCommand command = new ActivateProjectCommand(projectId);
        Project project = new Project(projectId, ownerId, "Proyecto Ajeno", ProjectStatus.DRAFT, false);

        when(currentUserPort.getCurrentUserId()).thenReturn(strangerId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act & Assert
        // Tu servicio lanza IllegalStateException si no es el dueño
        assertThrows(IllegalStateException.class, () -> activateProjectService.execute(command));
        verify(projectRepository, never()).save(any());
    }
}