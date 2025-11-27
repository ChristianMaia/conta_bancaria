package com.example.conta_bancaria_mqtt.interface_ui.controller;

import com.example.conta_bancaria_mqtt.application.dto.*;
import com.example.conta_bancaria_mqtt.application.service.TaxaService;
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

@Tag(name = "Taxa", description = "Definir taxas para certos pagamentos")
@RestController
@RequestMapping("/api/taxa")
@RequiredArgsConstructor
public class TaxaController {

    private final TaxaService service;

    @Operation(
            summary = "Registrar nova taxa",
            description = "Cadastra uma nova taxa ao sistema",
            parameters = {
                    @Parameter(name = "id", description = "O id da taxa que vai ser buscado", example = "1")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TaxaRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo de registro", value = """
                        {
                           "descricao": "Taxa minha taxa haddad, taxa minha taxa agora!"
                           "percentual": "0.1"
                           
                           OU
                           
                           "descricao": "Taxa minha taxa haddad, taxa minha taxa agora!"
                           "valorfixo": "50"
                            }
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Taxa registrado com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Taxa registrado com sucesso")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação - BAD REQUEST"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TaxaResponseDTO> registrarTaxa(@Valid @RequestBody TaxaRegistroDTO dto) {
        return ResponseEntity.ok(service.registrarTaxa(dto));
    }

    @Operation(
            summary = "Listar todas as taxas",
            description = "Retorna uma lista com todas as taxas registradas no sistema",
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
    public ResponseEntity<List<TaxaResponseDTO>> listarClientesAtivos() {
        return ResponseEntity.ok(service.listarTaxasAtivas());
    }

    @Operation(
            summary = "Buscar taxa por Id",
            description = "Retorna uma taxa informada pelo ID enviado",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID da taxa a ser buscado",
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Taxa encontrada",
                            content = @Content(
                                    examples = @ExampleObject(value = "Taxa encontrada com sucesso")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Taxa do ID 594 não encontrada\"")
                            )
                    )
            }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<TaxaResponseDTO> buscarTaxaporId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarTaxaPorId(id));
    }

    @Operation(
            summary = "Atualizar o percentual de uma taxa",
            description = "Atualiza o percentual de uma taxa já cadastrada no sistema",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID da taxa a ser atualizada",
                            example = "1"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TaxaRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                          "descricao": "Taxa minha taxa haddad, taxa minha taxa agora!"
                          "percentual": "0.1"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Taxa atualizada com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Taxa registrada com sucesso")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Taxa vazia", value = "\"Percentual não pode ser vazio\"")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Taxa com o ID 254 não foi encontrado\"")
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaxaResponseDTO> atualizarPercentual(@PathVariable Long id,
                                                                @Valid @RequestBody TaxaRegistroDTO dto) {
        return ResponseEntity.ok(service.atualizarPercentual(id, dto));
    }

    @Operation(
            summary = "Atualizar o valor fixo de uma taxa",
            description = "Atualiza o valor fixo de uma taxa já cadastrada no sistema",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID da taxa a ser atualizada",
                            example = "1"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TaxaRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                          "descricao": "Taxa minha taxa haddad, taxa minha taxa agora!"
                          "percentual": "0.1"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Taxa atualizada com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Taxa registrada com sucesso")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Taxa vazia", value = "\"Valor fixo não pode ser vazio\"")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Taxa com o ID 254 não foi encontrado\"")
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaxaResponseDTO> atualizarValorFixo(@PathVariable Long id,
                                                               @Valid @RequestBody TaxaRegistroDTO dto) {
        return ResponseEntity.ok(service.atualizarValorFixo(id, dto));
    }

    @Operation(
            summary = "Deletar Taxa",
            description = "Remove uma taxa do sistema (sem Haddad)",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID da taxa a ser deletado",
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Taxa removida com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(value = "Taxa removida com sucesso")
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Taxa com o ID 710 não foi encontrado\"")
                            )
                    )
            }
    )
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarTaxa(@PathVariable Long id) {
        service.deletarTaxa(id);
        return ResponseEntity.noContent().build();
    }
}

