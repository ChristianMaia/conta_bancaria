package com.example.conta_bancaria_mqtt.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "Pagamentos")
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

    @OneToMany(fetch = FetchType.LAZY)
    private List<Taxa> taxas;

    //fetch = FetchType.LAZY
}
