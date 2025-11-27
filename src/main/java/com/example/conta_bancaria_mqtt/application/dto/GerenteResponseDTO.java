package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Gerente;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record GerenteResponseDTO(
        Long id,

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
    public static GerenteResponseDTO fromEntity(Gerente gerente){
        return new GerenteResponseDTO(
                gerente.getId(),
                gerente.getNome(),
                gerente.getCpf(),
                gerente.getSenha()
        );
    }
}
