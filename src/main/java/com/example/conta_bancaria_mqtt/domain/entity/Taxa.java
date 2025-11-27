package com.example.conta_bancaria_mqtt.domain.entity;

import com.example.conta_bancaria_mqtt.domain.exception.TaxaMesmoBoletoException;
import com.example.conta_bancaria_mqtt.domain.exception.TaxaInvalidaException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Taxa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String descricao;

    protected BigDecimal percentual;

    protected BigDecimal valorFixo;

    public void validarTaxa(BigDecimal percentual, BigDecimal valorFixo) {
        boolean temPercentual = percentual != null && percentual.compareTo(BigDecimal.ZERO) > 0;
        boolean temValorFixo = valorFixo != null && valorFixo.compareTo(BigDecimal.ZERO) > 0;

        if (temPercentual && temValorFixo) {
            throw new TaxaMesmoBoletoException();
        }

        if (!temPercentual && !temValorFixo) {
            throw new TaxaInvalidaException();
        }

        if (percentual.compareTo(BigDecimal.ZERO) < 0 && valorFixo.compareTo(BigDecimal.ZERO) < 0){
            throw new TaxaInvalidaException();
        }

    }
}


