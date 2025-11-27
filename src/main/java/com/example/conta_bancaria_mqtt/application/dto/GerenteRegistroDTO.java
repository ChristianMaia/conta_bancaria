package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Gerente;
import com.example.conta_bancaria_mqtt.domain.enums.Role;
import jakarta.validation.constraints.*;

public record GerenteRegistroDTO(

        @NotNull(message = "O nome não pode ser nulo")
        @Size(min = 3, max = 300, message = "o nome pode ter entre 3 a 300 caracteres")
        String nome,

        @NotNull(message = "O CPF não pode ser nulo")
        @Positive
        @Pattern(regexp = "\\d{14}", message = "CPF deve conter 14 digitos numéricos")
        String cpf,

        @NotNull(message = "A senha não pode ser nula")
        @Size(min = 6, max = 24, message = "A senha deve ter entre 6 a 24 caracteres")
        String senha,

        ContaResumoDTO conta
) {
    public Gerente toEntity(){
        return Gerente.builder()
                .ativo(true)
                .nome(nome)
                .cpf(cpf)
                .senha(senha)
                .role(Role.GERENTE)
                .build();

    }
}
