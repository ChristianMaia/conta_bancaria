package com.example.conta_bancaria.application.dto;

public record ClienteRegistroDTO (
        String nome,
        String cpf,
        ContaDTO conta
){
}
