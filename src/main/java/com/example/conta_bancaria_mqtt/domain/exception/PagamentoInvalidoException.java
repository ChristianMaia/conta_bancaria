package com.example.conta_bancaria_mqtt.domain.exception;

public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException() {
        super("O pagamento foi invalido");
    }
}
