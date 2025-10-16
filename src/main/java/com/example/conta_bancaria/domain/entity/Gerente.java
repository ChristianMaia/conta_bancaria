package com.example.conta_bancaria.domain.entity;

import com.example.conta_bancaria.domain.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "gerentes")

public class Gerente extends Usuario{
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name="gerente_contas", joinColumns=@JoinColumn(name="gerente_id"))
        @Column(name="conta")
        private List<String > listaDeContas;

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name="gerente_clientes", joinColumns=@JoinColumn(name="gerente_id"))
        @Column(name="cliente")
        private List<String > listaDeCliente;

}
