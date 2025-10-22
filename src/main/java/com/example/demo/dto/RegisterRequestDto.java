package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {

    // ✅ NOVO: Garante que o campo não seja nulo ou vazio
    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Size(min = 3, max = 20, message = "O nome de usuário deve ter entre 3 e 20 caracteres.")
    private String username;

    // ✅ NOVO: Garante que o campo não seja nulo ou vazio
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 5, message = "A senha deve ter pelo menos 5 caracteres.")
    private String password;
}