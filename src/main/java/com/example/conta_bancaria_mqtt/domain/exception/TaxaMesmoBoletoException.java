package com.example.conta_bancaria_mqtt.domain.exception;

public class TaxaMesmoBoletoException extends RuntimeException {
    public TaxaMesmoBoletoException() {
        super("Percentual e valor fixo não deve ser inserido na mesma taxa!");
    }
}
