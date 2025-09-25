package com.example.conta_bancaria.interface_ui.controller;

import com.example.conta_bancaria.application.dto.ClienteAtualizadoDTO;
import com.example.conta_bancaria.application.dto.ClienteRegistroDTO;
import com.example.conta_bancaria.application.dto.ClienteResponseDTO;
import com.example.conta_bancaria.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@RequestBody ClienteRegistroDTO dto){
        var novoCliente = service.registrarCliente(dto);

        return ResponseEntity.created(URI.create("/api/cliente/cpf/"+
                novoCliente.cpf())).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos(){
        return ResponseEntity.ok(service.listarClientesAtivos());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClientesPorCPF(@PathVariable String cpf){
        return ResponseEntity.ok(service.buscarClientesPorCPF(cpf));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarClientes(@PathVariable String cpf,
                                                                @RequestBody ClienteAtualizadoDTO dto){

        return ResponseEntity.ok(service.atualizarCliente(cpf, dto));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpf){
        service.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}
