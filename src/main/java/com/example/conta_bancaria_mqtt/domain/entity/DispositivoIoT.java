package com.example.conta_bancaria_mqtt.domain.entity;


import jakarta.persistence.*;
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

    @Column(nullable = false)
    private boolean ativo;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
