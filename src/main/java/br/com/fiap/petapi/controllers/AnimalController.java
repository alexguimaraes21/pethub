package br.com.fiap.petapi.controllers;

import br.com.fiap.petapi.beans.AnimalBean;
import br.com.fiap.petapi.models.Animal;
import br.com.fiap.petapi.viewmodels.AnimalViewModel;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "${api.prefix}/${api.version.v1}/animal", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

    AnimalBean animalBean;

    @Autowired
    public AnimalController(AnimalBean animalBean) {
        this.animalBean = animalBean;
    }

    @Operation(summary = "Cadastra um Animal", security = {@SecurityRequirement(name = "token")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrar(
            @Valid @org.springframework.web.bind.annotation.RequestBody AnimalViewModel animalViewModel) {

        Animal animalCadastrado = animalBean.cadastrar(animalViewModel);
        if (animalCadastrado != null) {
            return new ResponseEntity<>(animalCadastrado, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Retorna detalhes de um Animal", security = {@SecurityRequirement(name = "token")})
    @GetMapping("/detalhe")
    public ResponseEntity<?> buscarPorId(
            @RequestParam(value = "id", required = false) Long animalId
    ) {
        Optional<Animal> animal = animalBean.buscarPorId(animalId);
        if (animal.isPresent()) {
            return new ResponseEntity<>(animal, HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Lista todos os Animais", security = {@SecurityRequirement(name = "token")})
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<Animal> animais = animalBean.listarAnimais();
        if (!animais.isEmpty()) {
            return new ResponseEntity<>(animais, HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Remove um animal", security = {@SecurityRequirement(name = "token")})
    @DeleteMapping
    public ResponseEntity<?> remover(
            @RequestParam(value = "id", required = false) Long animalId
    ) {
        animalBean.remover(animalId);
        return new ResponseEntity<>("[]", HttpStatus.OK);
    }
}
