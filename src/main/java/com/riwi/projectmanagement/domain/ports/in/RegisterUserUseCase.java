package com.riwi.projectmanagement.domain.ports.in;

public interface RegisterUserUseCase {

    /**
     * Registra un nuevo usuario en el sistema
     * @param command Datos del usuario a registrar
     * @return Token JWT del usuario registrado
     */
    String execute(RegisterUserCommand command);
}
