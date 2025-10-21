package com.example.conta_bancaria.application.dto;

import com.example.conta_bancaria.domain.entity.Cliente;
import com.example.conta_bancaria.domain.entity.Conta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;

public record ClienteRegistroDTO (
        @NotBlank(message = "O nome não pode estar vazio.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
        String nome,
        @NotBlank(message = "O CPF não pode estar vazio")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 digitos numéricos")
        String cpf,
        @NotBlank(message = "Sua senha não pode ser vazia")
        @Size(min = 6, max = 25, message = "A senha deve conter 6 a 25 caracteres")
        String senha,

        ContaResumoDTO contaDTO
){
    public Cliente toEntity() {
        return Cliente.builder()
                .ativo(true)
                .nome(this.nome)
                .cpf(this.cpf)
                .senha(this.senha)
                .contas(new ArrayList<Conta>())
                .build();
    }
}
