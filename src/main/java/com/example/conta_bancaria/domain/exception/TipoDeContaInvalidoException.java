package com.example.conta_bancaria.domain.exception;

public class TipoDeContaInvalidoException extends RuntimeException {
    public TipoDeContaInvalidoException(String tipo) {
        super("Tipo de conta"+ tipo+"inválida. Os tipos válidos são: 'CORRENTE', 'POUPANCA'.");
    }
}
