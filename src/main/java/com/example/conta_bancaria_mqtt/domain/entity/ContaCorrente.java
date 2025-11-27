package com.example.conta_bancaria_mqtt.domain.entity;


import com.example.conta_bancaria_mqtt.domain.exception.SaldoInsuficienteException;
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
@DiscriminatorValue("CORRENTE")
@EqualsAndHashCode(callSuper = true)
public class ContaCorrente extends Conta {

    @Column(precision = 19, scale = 2)
    private BigDecimal limite;

    @Column(precision = 19, scale = 2)
    private BigDecimal taxa;

    @Override
    public String getTipo(){
        return "CORRENTE";
    }

    @Override
    public void sacar(BigDecimal valor){
        validarValorMaiorQueZero(valor, "saque");

        BigDecimal custoSaque = valor.multiply(taxa);
        BigDecimal totalSaque = valor.add(custoSaque);

        if (this.getSaldo().add(this.limite).compareTo(totalSaque)<0){
            throw new SaldoInsuficienteException("saque");
        }
        this.setSaldo(this.getSaldo().subtract(totalSaque));
    }

}
