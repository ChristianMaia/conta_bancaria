package com.example.conta_bancaria.interface_ui.controller;

import com.example.conta_bancaria.application.dto.*;
import com.example.conta_bancaria.application.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Conta", description = "Gerenciamento das contas de banco")
@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService service;

    @Operation(
            summary = "Listar todas as contas",
            description = "Retorna todas as contas registradas",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Lista retornada com sucesso",
                            content = @Content(
                            examples = @ExampleObject(value = "Lista retornada com sucesso")
                    ))

            }
    )
    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarTodasAsContas(){
        return ResponseEntity.ok(service.listarTodasContas());
    }

    @Operation(
            summary = "Buscar conta por numero",
            description = "Retorna uma conta existente a partir do numero",
            parameters = {
                    @Parameter(name = "numero", description = "Numero da conta a ser buscado", example = "1001-XX")
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Conta encontrada",
                            content = @Content(
                                    examples = @ExampleObject(value = "Conta encontrada com sucesso")
                            )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com o numero XXXX-XX não encontrado.\"")

                            )
                    )
            }
    )
    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> buscarContaPorNumero(@PathVariable String numero) {
        return ResponseEntity.ok(service.buscarContaPorNumero(numero));
    }

    @Operation(
            summary = "Atualizar uma conta",
            description = "Atualiza os dados de uma conta ja existente",
            parameters = {
                    @Parameter(name = "numero", description = "Numero da conta a ser atualizado", example = "1001-XX")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteAtualizadoDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                           "saldo":"200.00",
                           "limite":"690.00",
                           "rendimento":"130.00",
                           "taxa":"50.00"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Conta atualizado com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Conta atualizado com sucesso")
                            )),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo negativo", value = "\"O saldo não pode ser negativo\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com o numero XXXX-XX não encontrado.\"")
                            )
                    )
            }
    )
    @PutMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> atualizarConta(@PathVariable String numero,
                                                         @Valid @RequestBody ContaAtualizacaoDTO dto){
        return ResponseEntity.ok(service.atualizarConta(numero, dto));
    }

    @Operation(
            summary = "Deletar uma conta",
            description = "Remove uma conta da base de dados a partir do seu numeor",
            parameters = {
                    @Parameter(name = "numero", description = "Numero da conta a ser deletado", example = "1001-XX")
            },
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Conta removida com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Conta removida com sucesso")
                            )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com o numero XXXX-XX não encontrado.\"")
                            )
                    )
            }
    )
    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletarConta(@PathVariable String numero){
        service.deletarConta(numero);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Sacar da conta",
            description = "Sacar de uma conta ja existente",
            parameters = {
                    @Parameter(name = "numero", description = "Numero da conta que ira ser sacada", example = "1001-XX")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ValorSaqueDepositoDTO.class),
                            examples = @ExampleObject(name = "Exemplo de saque", value = """
                        {
                           "valor":"200.00"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Conta sacada com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Saque da conta com sucesso")
                            )),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo negativo", value = "\"O saldo não pode ser negativo\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com o numero XXXX-XX não encontrado.\"")
                            )
                    )
            }
    )
    @PostMapping("/{numero}/sacar")
    public ResponseEntity<ContaResumoDTO> sacar(@PathVariable String numero,
                                                @Valid @RequestBody ValorSaqueDepositoDTO dto){
        return ResponseEntity.ok(service.sacar(numero, dto));
    }

    @Operation(
            summary = "Depositar na conta",
            description = "Depositar em uma conta ja existente",
            parameters = {
                    @Parameter(name = "numero", description = "Numero da conta que ira depositar", example = "1001-XX")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ValorSaqueDepositoDTO.class),
                            examples = @ExampleObject(name = "Exemplo de deposito", value = """
                        {
                           "valor":"200.00"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Conta depositada com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Conta depositada com sucesso")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo negativo", value = "\"O saldo não pode ser negativo\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com o numero XXXX-XX não encontrado.\"")
                            )
                    )
            }
    )
    @PostMapping("/{numero}/depositar")
    public ResponseEntity<ContaResumoDTO> depositar(@PathVariable String numero,
                                                    @Valid @RequestBody ValorSaqueDepositoDTO dto){
        return ResponseEntity.ok(service.depositar(numero, dto));
    }

    @Operation(
            summary = "Tranferir saldo",
            description = "Transferir o saldo de uma conta para outra conta",
            parameters = {
                    @Parameter(name = "numero", description = "Numero da conta que ira transferir", example = "1001-XX")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TransferenciaDTO.class),
                            examples = @ExampleObject(name = "Exemplo de transferencia", value = """
                        {
                           "contaDestino": "2143-09",
                           "valor":"200.00"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Saldo transferido com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Saldo foi transferido com sucesso")
                            )),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo negativo", value = "\"O saldo não pode ser negativo\""),
                                            @ExampleObject(name = "Transferir mesma conta", value = "\"Não é possivel transferir o saldo para a mesma conta\"")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com o numero XXXX-XX não encontrado.\"")
                            )
                    )
            }
    )
    @PostMapping("/{numero}/transferir")
    public ResponseEntity<ContaResumoDTO> transferir(@PathVariable String numero,
                                                     @Valid @RequestBody TransferenciaDTO dto){
        return ResponseEntity.ok(service.transferir(numero, dto));
    }
    @Operation(
            summary = "Colocar rendimento na conta",
            description = "Colocar um rendimento em uma conta ja existente",
            parameters = {
                    @Parameter(name = "numero", description = "Numero da conta que ira ter rendimento", example = "1001-XX")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ValorSaqueDepositoDTO.class),
                            examples = @ExampleObject(name = "Exemplo de rendimento", value = """
                        {
                           "valor":"0.05"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Conta obteve rendimento com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Conta tem rendimento agora com sucesso")
                            )),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo negativo", value = "\"O saldo não pode ser negativo\""),
                                            @ExampleObject(name = "Rendimento invalido", value = "\"O Rendimento deve ser colocado em contas de poupança\"")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com o numero XXXX-XX não encontrado.\"")
                            )
                    )
            }
    )
    @PostMapping("/{numeroDaConta}/rendimento")
        public ResponseEntity<ContaResumoDTO> aplicarRendimento(@PathVariable String numeroDaConta){
            return ResponseEntity.ok(service.aplicarRendimento(numeroDaConta));
        }
}
