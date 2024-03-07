package br.com.fiap.petapi.controllers;

import br.com.fiap.petapi.beans.ArquivoBean;
import br.com.fiap.petapi.lib.util.SystemMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "${api.prefix}/${api.version.v1}/arquivo",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ArquivoController {

    ArquivoBean arquivoBean;

    @Autowired
    public ArquivoController(ArquivoBean arquivoBean) {
        this.arquivoBean = arquivoBean;
    }

    @Operation(summary = "Upload de arquivos", security = {@SecurityRequirement(name = "token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SystemMessages.OK_MESSAGE)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(
            @Parameter(description = "Arquivo a ser enviado", required = true, content =
            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart(value = "arquivo", required = true) MultipartFile file) throws IOException {
        arquivoBean.cadastraArquivo(file);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
