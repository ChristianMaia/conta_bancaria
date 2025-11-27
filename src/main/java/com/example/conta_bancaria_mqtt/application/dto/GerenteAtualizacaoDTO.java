package com.example.conta_bancaria_mqtt.application.dto;

import jakarta.validation.constraints.*;

public record GerenteAtualizacaoDTO(
        @NotNull(message = "O nome não pode ser nulo")
        @Size(min = 3, max = 300, message = "o nome pode ter entre 3 a 300 caracteres")
        String nome,

        @NotNull(message = "O CPF não pode ser nulo")
        @Positive
        @Pattern(regexp = "\\d{14}", message = "CPF deve conter 14 digitos numéricos")
        String cpf,

        @NotNull(message = "A senha não pode ser nula")
        @Size(min = 6, max = 24, message = "A senha deve ter entre 6 a 24 caracteres")
        String senha

) {
}
