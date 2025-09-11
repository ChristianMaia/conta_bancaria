package com.example.conta_bancaria.application.dto;

import com.example.conta_bancaria.domain.entity.Cliente;

import java.util.List;
import java.util.stream.Collectors;

public record ClienteDTO(
    Long id,
    String nome,
    String cpf,
    List<ContaDTO> contas
) {
    public static ClienteDTO fromEntity(Cliente cliente) {
        if (cliente == null) return null;
        List<ContaDTO> contasDto = cliente.getContas() == null ? List.of() :
                cliente.getContas()
                        .stream()
                        .map(ContaDTO::fromEntity)
                        .collect(Collectors.toList());

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                contasDto
        );
    }

    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        return cliente;
    }
}
