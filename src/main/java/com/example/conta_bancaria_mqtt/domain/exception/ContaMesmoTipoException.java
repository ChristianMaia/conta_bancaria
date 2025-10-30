package com.example.conta_bancaria_mqtt.domain.exception;

public class ContaMesmoTipoException extends RuntimeException {
    public ContaMesmoTipoException() {
        super("Não é possivel transferir para uma conta do mesmo tipo.");
    }
}
