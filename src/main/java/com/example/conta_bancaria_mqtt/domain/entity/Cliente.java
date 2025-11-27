package com.example.conta_bancaria_mqtt.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "clientes",
uniqueConstraints = {
           @UniqueConstraint(columnNames = "cpf")
       })
public class Cliente  extends Usuario{
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Conta> contas;

    @Transient
    private CodigoAutenticacao autenticacao;

    @Transient
    private DispositivoIoT ioT;


}
