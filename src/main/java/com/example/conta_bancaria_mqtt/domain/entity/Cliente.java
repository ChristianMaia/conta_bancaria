package com.example.conta_bancaria_mqtt.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@SuperBuilder

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes",
uniqueConstraints = {
           @UniqueConstraint(columnNames = "cpf")
       })
public class Cliente  extends Usuario{
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Conta> contas;
}
