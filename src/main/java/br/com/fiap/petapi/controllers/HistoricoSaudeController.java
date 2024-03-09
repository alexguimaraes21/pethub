package br.com.fiap.petapi.controllers;

import br.com.fiap.petapi.beans.HistoricoSaudeBean;
import br.com.fiap.petapi.models.HistoricoSaude;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "${api.prefix}/${api.version.v1}/historico-saude", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoricoSaudeController {

    HistoricoSaudeBean historicoSaudeBean;

    @Autowired
    public HistoricoSaudeController(HistoricoSaudeBean historicoSaudeBean) {
        this.historicoSaudeBean = historicoSaudeBean;
    }

    @Operation(summary = "Retorna detalhes do histórico de saúde de um animal", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = HistoricoSaude.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/detalhes")
    public ResponseEntity<?> buscarPorId(
            @RequestParam(value = "historicoSaudeId", required = false) Long historicoSaudeId,
            @RequestParam(value = "animalId", required = false) Long animalId
    ) {
        Optional<HistoricoSaude> historicoSaude = historicoSaudeBean.buscarPorId(historicoSaudeId, animalId);
        if (historicoSaude.isPresent()) {
            return new ResponseEntity<HistoricoSaude>(historicoSaude.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Retorna todo histórico de saúde de um animal", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema( schema = @Schema(implementation = HistoricoSaude.class))) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @GetMapping
    public ResponseEntity<?> listarHistoricoSaude(
            @RequestParam(value = "animalId", required = false) Long animalId
    ) {
        List<HistoricoSaude> historicoSaude = historicoSaudeBean.listarHistoricoSaude(animalId);
        if (!historicoSaude.isEmpty()) {
            return new ResponseEntity<>(historicoSaude, HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrarVacina() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
