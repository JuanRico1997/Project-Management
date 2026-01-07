package com.riwi.projectmanagement.infrastructure.adapters.in.rest.controllers;

import com.riwi.projectmanagement.domain.models.Task;
import com.riwi.projectmanagement.domain.ports.in.CompleteTaskCommand;
import com.riwi.projectmanagement.domain.ports.in.CompleteTaskUseCase;
import com.riwi.projectmanagement.infrastructure.adapters.in.rest.dto.response.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller REST - Para operaciones individuales de tareas
 */
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "API para gestión de tareas")
public class TaskManagementController {

    private final CompleteTaskUseCase completeTaskUseCase;

    public TaskManagementController(CompleteTaskUseCase completeTaskUseCase) {
        this.completeTaskUseCase = completeTaskUseCase;
    }

    /**
     * Endpoint: PATCH /api/tasks/{id}/complete
     * Marca una tarea como completada
     */
    @PatchMapping("/{id}/complete")
    @Operation(
            summary = "Completar tarea",
            description = "Marca una tarea como completada. No puede estar ya completada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea completada exitosamente"),
            @ApiResponse(responseCode = "400", description = "La tarea ya está completada"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No eres el propietario del proyecto"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<TaskResponse> completeTask(@PathVariable UUID id) {
        CompleteTaskCommand command = new CompleteTaskCommand(id);
        Task completedTask = completeTaskUseCase.execute(command);
        TaskResponse response = TaskResponse.fromDomain(completedTask);
        return ResponseEntity.ok(response);
    }
}
