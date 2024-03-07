package br.com.fiap.petapi.controllers;

import br.com.fiap.petapi.lib.util.SystemMessages;
import br.com.fiap.petapi.responsemodel.PingResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("${api.prefix}/${api.version.v1}/ping")
public class PingController {

    @Operation(summary = "Retorna se a API está funcional, apresentando a data e hora atual", security = { @SecurityRequirement(name = "token") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SystemMessages.OK_MESSAGE,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PingResponseModel.class)))
    })
    @GetMapping
    public ResponseEntity<?> index() {
        PingResponseModel pingResponseModel = new PingResponseModel("ping", LocalDateTime.now());

        return new ResponseEntity<>(pingResponseModel, HttpStatus.OK);
    }
}
