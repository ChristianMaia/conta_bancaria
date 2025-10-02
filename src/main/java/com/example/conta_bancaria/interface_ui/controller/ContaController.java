package com.example.conta_bancaria.interface_ui.controller;

import com.example.conta_bancaria.application.dto.ContaAtualizacaoDTO;
import com.example.conta_bancaria.application.dto.ContaResumoDTO;
import com.example.conta_bancaria.application.dto.TransferenciaDTO;
import com.example.conta_bancaria.application.dto.ValorSaqueDepositoDTO;
import com.example.conta_bancaria.application.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService service;

    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarTodasAsContas(){
        return ResponseEntity.ok(service.listarTodasContas());
    }

    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> buscarContaPorNumero(@PathVariable String numero) {
        return ResponseEntity.ok(service.buscarContaPorNumero(numero));
    }

    @PutMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> atualizarConta(@PathVariable String numero,
                                                         @RequestBody ContaAtualizacaoDTO dto){
        return ResponseEntity.ok(service.atualizarConta(numero, dto));
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletarConta(@PathVariable String numero){
        service.deletarConta(numero);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{numero}/sacar")
    public ResponseEntity<ContaResumoDTO> sacar(@PathVariable String numero,
                                                @RequestBody ValorSaqueDepositoDTO dto){
        return ResponseEntity.ok(service.sacar(numero, dto));
    }

    @PostMapping("/{numero}/depositar")
    public ResponseEntity<ContaResumoDTO> depositar(@PathVariable String numero,
                                                    @RequestBody ValorSaqueDepositoDTO dto){
        return ResponseEntity.ok(service.depositar(numero, dto));
    }

    @PostMapping("/{numero}/transferir")
    public ResponseEntity<ContaResumoDTO> transferir(@PathVariable String numero,
                                                     @RequestBody TransferenciaDTO dto){
        return ResponseEntity.ok(service.transferir(numero, dto));
    }
}
