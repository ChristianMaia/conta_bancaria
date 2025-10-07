package com.example.conta_bancaria.domain.exception;

public class EntidadeNaoEncontradoException extends RuntimeException {
    public EntidadeNaoEncontradoException(String entidade) {
        super(entidade + " não existente ou inativo(a)!");
    }
}
