package com.example.conta_bancaria_mqtt.domain.exception;

public class ValoresNegativosException extends RuntimeException {
    public ValoresNegativosException(String operacao) {
        super("Não é possivel realizar "+operacao+" com valores negativos.");
    }
}
