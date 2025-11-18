package com.example.conta_bancaria_mqtt.domain.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class DispositivoIoT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull(message = "O codigo serial não deve ser nula")
    @NotBlank(message = "O codigo serial não deve ser vazia")
    protected Long codigoSerial;

    @NotNull(message = "A chave pública não deve ser nula")
    @NotBlank(message = "A chave pública não deve ser vazia")
    protected String chavePublica;

    protected String ativo;

    @OneToOne
    protected Cliente cliente;
}
