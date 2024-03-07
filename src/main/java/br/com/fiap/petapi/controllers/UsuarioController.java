package br.com.fiap.petapi.controllers;

import br.com.fiap.petapi.beans.UsuarioBean;
import br.com.fiap.petapi.lib.util.SystemMessages;
import br.com.fiap.petapi.models.Usuario;
import br.com.fiap.petapi.services.UsuarioService;
import br.com.fiap.petapi.viewmodels.PessoaViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "${api.prefix}/${api.version.v1}/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    UsuarioService usuarioService;
    UsuarioBean usuarioBean;

    @Autowired
    public UsuarioController(UsuarioService usuarioService,
                             UsuarioBean usuarioBean) {
        this.usuarioService = usuarioService;
        this.usuarioBean = usuarioBean;
    }

    @Operation(summary = "Retorna informações do Usuário Autenticado", security = { @SecurityRequirement(name = "token") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SystemMessages.OK_MESSAGE,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Usuario.class)))
    })
    @GetMapping
    public ResponseEntity<?> retornaInfo() {
        Optional<Usuario> usuario = usuarioService.retornaUsuarioAutenticado();
        if(usuario.isPresent()) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Cadastra um Usuário", security = { @SecurityRequirement(name = "token") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SystemMessages.CREATED_MESSAGE,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = SystemMessages.BAD_REQUEST_MESSAGE,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PessoaViewModel.class)))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrar(@RequestBody(description = "Cadastro de novo usuário",
            required = true, content = @Content(schema = @Schema(implementation = PessoaViewModel.class)))
                                                       @Valid @org.springframework.web.bind.annotation.RequestBody PessoaViewModel pessoaViewModel,
                                                   @CurrentSecurityContext(expression = "authentication") Authentication authentication)
    {
        Usuario usuario = usuarioBean.cadastraUsuario(authentication.getName(), pessoaViewModel);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
