package com.example.conta_bancaria.domain.entity;

import com.example.conta_bancaria.domain.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
