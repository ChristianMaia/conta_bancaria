package com.example.conta_bancaria_mqtt.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "codigo_autenticacao")
public class CodigoAutenticacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @NotBlank
    protected String codigo;

    @NotBlank
    protected LocalDateTime expiraEm;

    @NotBlank
    protected boolean validado;

    @NotBlank
    @OneToOne
    protected Cliente cliente;
}
