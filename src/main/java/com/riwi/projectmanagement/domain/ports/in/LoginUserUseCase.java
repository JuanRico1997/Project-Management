package com.riwi.projectmanagement.domain.ports.in;

/**
 * Puerto IN - Define el caso de uso "Login Usuario"
 */
public interface LoginUserUseCase {
    /**
     * Autentica un usuario y genera un token JWT
     * @param command Credenciales del usuario
     * @return Token JWT
     */
    String execute(LoginUserCommand command);
}