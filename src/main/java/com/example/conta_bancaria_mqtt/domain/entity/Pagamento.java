package com.example.conta_bancaria_mqtt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    protected Conta conta;

    protected BigDecimal boleto;

    protected BigDecimal valorPago;

    protected String dataPagamento;

    protected String status;

    @ManyToMany
    protected Taxa taxa;
}
