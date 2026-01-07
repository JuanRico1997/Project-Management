package com.riwi.projectmanagement.infrastructure.adapters.in.rest.controllers;

import com.riwi.projectmanagement.domain.models.Task;
import com.riwi.projectmanagement.domain.ports.in.CompleteTaskCommand;
import com.riwi.projectmanagement.domain.ports.in.CompleteTaskUseCase;
import com.riwi.projectmanagement.domain.ports.in.DeleteTaskCommand;
import com.riwi.projectmanagement.domain.ports.in.DeleteTaskUseCase;
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
    private final DeleteTaskUseCase deleteTaskUseCase;

    public TaskManagementController(CompleteTaskUseCase completeTaskUseCase,
                                    DeleteTaskUseCase deleteTaskUseCase) {
        this.completeTaskUseCase = completeTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
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

    /**
     * Endpoint: DELETE /api/tasks/{id}
     * Elimina lógicamente una tarea
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar tarea",
            description = "Elimina lógicamente una tarea (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "La tarea ya está eliminada"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No eres el propietario del proyecto"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable UUID id) {
        // 1. Crear el command
        DeleteTaskCommand command = new DeleteTaskCommand(id);

        // 2. Ejecutar el caso de uso
        Task deletedTask = deleteTaskUseCase.execute(command);

        // 3. Convertir a Response
        TaskResponse response = TaskResponse.fromDomain(deletedTask);

        // 4. Devolver respuesta HTTP 200 OK
        return ResponseEntity.ok(response);
    }
}
