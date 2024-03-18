package br.com.fiap.petapi.controllers;

import br.com.fiap.petapi.beans.HistoricoSaudeBean;
import br.com.fiap.petapi.models.HistoricoSaude;
import br.com.fiap.petapi.responsemodel.AnimalResponseModel;
import br.com.fiap.petapi.responsemodel.HistoricoSaudeResponseModel;
import br.com.fiap.petapi.viewmodels.HistoricoSaudeViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = HistoricoSaudeResponseModel.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/detalhes")
    public ResponseEntity<?> buscarPorId(
            @RequestParam(value = "historicoSaudeId", required = false) Long historicoSaudeId,
            @RequestParam(value = "animalId", required = false) Long animalId
    ) {
        HistoricoSaudeResponseModel historicoSaude = historicoSaudeBean.buscarPorId(historicoSaudeId, animalId);
        if (historicoSaude != null) {
            return new ResponseEntity<>(historicoSaude, HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.OK);
    }

    @Operation(summary = "Retorna todo histórico de saúde de um animal", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema( schema = @Schema(implementation = HistoricoSaudeResponseModel.class))) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @GetMapping
    public ResponseEntity<?> listarHistoricoSaude(
            @RequestParam(value = "animalId", required = false) Long animalId
    ) {
        List<HistoricoSaudeResponseModel> historicoSaude = historicoSaudeBean.listarHistoricoSaude(animalId);
        if (!historicoSaude.isEmpty()) {
            return new ResponseEntity<>(historicoSaude, HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.OK);
    }

    @Operation(summary = "Cadastra um item no Prontuário", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = HistoricoSaudeResponseModel.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrar(
            @Valid @RequestBody HistoricoSaudeViewModel historicoSaudeViewModel,
            @RequestParam(value = "animalId", required = false) Long animalId) {
        HistoricoSaudeResponseModel historicoSaudeResponseModel = historicoSaudeBean.cadastrar(animalId, historicoSaudeViewModel);
        if(historicoSaudeResponseModel != null) {
            return new ResponseEntity<>(historicoSaudeResponseModel, HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Remove item do histórico de saúde de um animal", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping
    public ResponseEntity<?> remover(
            @RequestParam(value = "historicoSaudeId", required = false) Long historicoSaudeId,
            @RequestParam(value = "animalId", required = false) Long animalId
    ) {
        boolean removido = historicoSaudeBean.remover(historicoSaudeId, animalId);
        if (removido) {
            return new ResponseEntity<>("[]", HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.NO_CONTENT);
    }
}
