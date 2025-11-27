package com.example.conta_bancaria_mqtt.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("POUPANCA")
public class ContaPoupanca extends Conta{

    @Column(precision = 19, scale = 2)
    private BigDecimal rendimento;

    @Override
    public String getTipo(){
        return "POUPANCA";
    }

    public void aplicarRendimento() {
        BigDecimal valorRendimento = getSaldo().multiply(rendimento);
        setSaldo(getSaldo().add(valorRendimento));
    }
}
