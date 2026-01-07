package com.riwi.projectmanagement.domain.ports.in;

/**
 * Command - Datos de entrada para crear un proyecto
 */
public class CreateProjectCommand {
    private final String name;

    public CreateProjectCommand(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proyecto es obligatorio");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }
}