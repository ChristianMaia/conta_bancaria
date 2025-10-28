package com.example.conta_bancaria.interface_ui.controller;

import com.example.conta_bancaria.application.dto.ClienteAtualizadoDTO;
import com.example.conta_bancaria.application.dto.ClienteRegistroDTO;
import com.example.conta_bancaria.application.dto.ClienteResponseDTO;
import com.example.conta_bancaria.application.service.ClienteService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Cliente", description = "Gerenciamento de clientes do banco")
@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @Operation(
            summary = "Registrar novo cliente",
            description = "Cadastra um novo cliente no sistema bancário",
            parameters = {
                    @Parameter(name = "cpf", description = "CPF do cliente a ser buscado", example = "111.111.111-11")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo de registro", value = """
                        {
                           "nome": "Christian",
                           "cpf": 39333335421,
                           "senha": "1234",
                            "contaDTO": {
                            "numero": "9600-37",
                            "tipo": "CORRENTE",
                            "saldo": 659
                            }
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente registrado com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Cliente registrado com sucesso")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "CPF inválido", value = "\"CPF inválido\""),
                                            @ExampleObject(name = "CPF já cadastrado", value = "\"CPF já cadastrado no sistema\"")
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@Valid @RequestBody ClienteRegistroDTO dto) {
        return ResponseEntity.ok(service.registrarCliente(dto));

    }

    @Operation(
            summary = "Listar todos os clientes ativos",
            description = "Retorna uma lista com todos os clientes ativos cadastrados no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista retornada com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Lista encontrada com sucesso")
                            )

                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos() {
        return ResponseEntity.ok(service.listarClientesAtivos());
    }

    @Operation(
            summary = "Buscar cliente por CPF",
            description = "Retorna um cliente específico a partir do CPF informado",
            parameters = {
                    @Parameter(
                            name = "cpf",
                            description = "CPF do cliente a ser buscado",
                            example = "111-111-111.11"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente encontrado",
                            content = @Content(
                                examples = @ExampleObject(value = "Cliente encontrado com sucesso")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Cliente com CPF 111-111-111.11 não encontrado.\"")
                            )
                    )
            }
    )
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClientesPorCPF(@PathVariable String cpf) {
        return ResponseEntity.ok(service.buscarClientesPorCPF(cpf));
    }

    @Operation(
            summary = "Atualizar dados do cliente",
            description = "Atualiza os dados de um cliente já cadastrado no sistema",
            parameters = {
                    @Parameter(
                            name = "cpf",
                            description = "CPF do cliente a ser atualizado",
                            example = "111-111-111.11"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteAtualizadoDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                           "nome": "João Silva Santos",
                           "cpf": "111-111-111.11"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente atualizado com sucesso",
                            content = @Content(
                                examples = @ExampleObject(value = "Cliente registrado com sucesso")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Nome vazio", value = "\"Nome não pode ser vazio\"")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Cliente com CPF 111-111-111.11 não encontrado.\"")
                            )
                    )
            }
    )
    @PutMapping("/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarClientes(@PathVariable String cpf,
                                                                @Valid @RequestBody ClienteAtualizadoDTO dto) {
        return ResponseEntity.ok(service.atualizarCliente(cpf, dto));
    }

    @Operation(
            summary = "Deletar cliente",
            description = "Remove um cliente do sistema (desativação lógica)",
            parameters = {
                    @Parameter(
                            name = "cpf",
                            description = "CPF do cliente a ser deletado",
                            example = "111-111-111.11"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Cliente removido com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Cliente removido com sucesso")
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Cliente com CPF 111-111-111.11 não encontrado.\"")
                            )
                    )
            }
    )
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpf) {
        service.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}