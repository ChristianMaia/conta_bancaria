package com.example.conta_bancaria_mqtt.application.dto;

import com.example.conta_bancaria_mqtt.domain.entity.Cliente;
import com.example.conta_bancaria_mqtt.domain.entity.Conta;
import com.example.conta_bancaria_mqtt.domain.entity.ContaCorrente;
import com.example.conta_bancaria_mqtt.domain.entity.ContaPoupanca;
import com.example.conta_bancaria_mqtt.domain.exception.TipoDeContaInvalidoException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ContaResumoDTO(
        @NotBlank
        @Pattern(regexp = "\\d{5,20}", message = "Número da conta deve conter entre 5 a 20 digitos")
        String numero,

        String tipo,
        BigDecimal saldo
) {
     public Conta toEntity(Cliente cliente){
         if ("CORRENTE".equalsIgnoreCase(this.tipo)){
                return ContaCorrente.builder()
                        .cliente(cliente)
                        .numero(numero)
                        .saldo(saldo)
                        .taxa(new BigDecimal("0.05"))
                        .limite(new BigDecimal("500.00"))
                        .ativa(true)
                        .build();
            } else if ("POUPANCA".equalsIgnoreCase(this.tipo)) {
             return ContaPoupanca.builder()
                     .cliente(cliente)
                     .numero(numero)
                     .saldo(saldo)
                     .rendimento(new BigDecimal("0.03"))
                     .ativa(true)
                     .build();
         }
         throw new TipoDeContaInvalidoException(tipo);
     }

    public static ContaResumoDTO FromEntity(Conta conta) {
         return new ContaResumoDTO(
                 conta.getNumero(),
                 conta.getTipo(),
                 conta.getSaldo()
         );
    }
}
