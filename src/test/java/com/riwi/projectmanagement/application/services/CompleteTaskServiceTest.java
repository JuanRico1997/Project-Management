package com.riwi.projectmanagement.application.services;

import com.riwi.projectmanagement.application.sevices.CompleteTaskService;
import com.riwi.projectmanagement.domain.models.Project;
import com.riwi.projectmanagement.domain.models.ProjectStatus;
import com.riwi.projectmanagement.domain.models.Task;
import com.riwi.projectmanagement.domain.ports.in.CompleteTaskCommand;
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
class CompleteTaskServiceTest {

    @Mock private TaskRepositoryPort taskRepository;
    @Mock private ProjectRepositoryPort projectRepository;
    @Mock private CurrentUserPort currentUserPort;
    @Mock private AuditLogPort auditLogPort;
    @Mock private NotificationPort notificationPort;

    @InjectMocks
    private CompleteTaskService completeTaskService;

    @Test
    @DisplayName("CompleteTask_AlreadyCompleted_ShouldFail: No permite completar si ya está completada")
    void completeTask_AlreadyCompleted_ShouldFail() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        CompleteTaskCommand command = new CompleteTaskCommand(taskId);

        // Tarea ya completada (completed = true)
        Task completedTask = new Task(taskId, projectId, "Tarea Test", true, false);
        Project project = new Project(projectId, userId, "Proyecto Test", ProjectStatus.ACTIVE, false);

        when(currentUserPort.getCurrentUserId()).thenReturn(userId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(completedTask));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act & Assert [cite: 123]
        assertThrows(IllegalStateException.class, () -> completeTaskService.execute(command));
        verify(taskRepository, never()).save(any());
    }

    @Test
    @DisplayName("CompleteTask_ShouldGenerateAuditAndNotification: Verifica flujo exitoso y puertos")
    void completeTask_ShouldGenerateAuditAndNotification() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        CompleteTaskCommand command = new CompleteTaskCommand(taskId);

        Task pendingTask = new Task(taskId, projectId, "Tarea Pendiente", false, false);
        Project project = new Project(projectId, userId, "Proyecto Test", ProjectStatus.ACTIVE, false);

        when(currentUserPort.getCurrentUserId()).thenReturn(userId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(pendingTask));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(pendingTask);

        // Act
        completeTaskService.execute(command);

        // Assert [cite: 124]
        verify(taskRepository).save(any(Task.class));
        verify(auditLogPort).register(eq("TASK_COMPLETED"), eq(taskId)); // Regla 5 [cite: 113]
        verify(notificationPort).notify(contains("ha sido completada")); // Regla 6 [cite: 114]
    }
}