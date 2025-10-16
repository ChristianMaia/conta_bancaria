package com.example.conta_bancaria.domain.entity;

import com.example.conta_bancaria.domain.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes",
uniqueConstraints = {
           @UniqueConstraint(columnNames = "cpf")
       })
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 125)
    private String nome;

    @NotBlank(message = "É necessario colocar seu CPF")
    @Size(min = 11, max = 11, message = "O seu CPF deve conter 11 digitos")
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Conta> contas;

    @Column(nullable = false)
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Role role;

}
