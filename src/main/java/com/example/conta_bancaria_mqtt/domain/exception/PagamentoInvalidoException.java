package com.example.conta_bancaria_mqtt.domain.exception;

public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException(String mensagem) {
        super("Falha em proceder com o pagamento: "+mensagem);
    }
}
