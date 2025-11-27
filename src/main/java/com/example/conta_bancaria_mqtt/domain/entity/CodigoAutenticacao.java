package com.example.conta_bancaria_mqtt.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CodigoAutenticacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @NotBlank
    protected String codigo;

    @NotBlank
    protected BigDecimal expiraEm;

    @NotBlank
    protected String validado;

    @NotBlank
    protected Cliente cliente;
}
