package com.example.conta_bancaria_mqtt.domain.exception;

public class TaxaInvalidaException extends RuntimeException {
    public TaxaInvalidaException() {
        super("A taxa foi invalida");
    }
}
