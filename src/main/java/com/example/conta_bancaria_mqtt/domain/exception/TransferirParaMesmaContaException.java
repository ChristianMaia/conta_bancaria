package com.example.conta_bancaria_mqtt.domain.exception;

public class TransferirParaMesmaContaException extends RuntimeException {
    public TransferirParaMesmaContaException() {
        super("Não é possivel transferir para a mesma conta.");
    }
}
