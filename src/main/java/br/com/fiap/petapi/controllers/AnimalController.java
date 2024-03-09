package br.com.fiap.petapi.controllers;

import br.com.fiap.petapi.beans.AnimalBean;
import br.com.fiap.petapi.models.Animal;
import br.com.fiap.petapi.models.HistoricoSaude;
import br.com.fiap.petapi.responsemodel.AnimalResponseModel;
import br.com.fiap.petapi.viewmodels.AnimalViewModel;
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

import java.util.ArrayList;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = AnimalResponseModel.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cadastrar(
            @Valid @org.springframework.web.bind.annotation.RequestBody AnimalViewModel animalViewModel) {

        Animal animalCadastrado = animalBean.cadastrar(animalViewModel);
        if (animalCadastrado != null) {
            AnimalResponseModel animalResponseModel = new AnimalResponseModel();
            animalResponseModel.setId(animalCadastrado.getId());
            animalResponseModel.setFoto("");
            animalResponseModel.setCor(animalCadastrado.getCor());
            animalResponseModel.setNome(animalCadastrado.getNome());
            animalResponseModel.setPeso(animalCadastrado.getPeso());
            animalResponseModel.setRaca(animalCadastrado.getRaca());
            animalResponseModel.setCodigoChip(animalCadastrado.getCodigoChip());
            animalResponseModel.setTemperamento(animalCadastrado.getTemperamento());
            animalResponseModel.setDataNascimento(animalCadastrado.getDataNascimento());
            animalResponseModel.setCodigoTatuagem(animalCadastrado.getCodigoTatuagem());
            animalResponseModel.setNomeCientifico(animalCadastrado.getNomeCientifico());
            animalResponseModel.setNomeEspecie(animalCadastrado.getNomeEspecie());
            animalResponseModel.setTamanhoPorte(animalCadastrado.getTamanhoPorte());
            return new ResponseEntity<>(animalCadastrado, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Retorna detalhes de um Animal", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = AnimalResponseModel.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/detalhes")
    public ResponseEntity<?> buscarPorId(
            @RequestParam(value = "id", required = false) Long animalId
    ) {
        Optional<Animal> animal = animalBean.buscarPorId(animalId);
        if (animal.isPresent()) {
            AnimalResponseModel animalResponseModel = new AnimalResponseModel();
            animalResponseModel.setId(animal.get().getId());
            animalResponseModel.setFoto("");
            animalResponseModel.setCor(animal.get().getCor());
            animalResponseModel.setNome(animal.get().getNome());
            animalResponseModel.setPeso(animal.get().getPeso());
            animalResponseModel.setRaca(animal.get().getRaca());
            animalResponseModel.setCodigoChip(animal.get().getCodigoChip());
            animalResponseModel.setTemperamento(animal.get().getTemperamento());
            animalResponseModel.setDataNascimento(animal.get().getDataNascimento());
            animalResponseModel.setCodigoTatuagem(animal.get().getCodigoTatuagem());
            animalResponseModel.setNomeCientifico(animal.get().getNomeCientifico());
            animalResponseModel.setNomeEspecie(animal.get().getNomeEspecie());
            animalResponseModel.setTamanhoPorte(animal.get().getTamanhoPorte());
            return new ResponseEntity<>(animalResponseModel, HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Lista todos os Animais", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema( schema = @Schema(implementation = AnimalResponseModel.class))) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @GetMapping
    public ResponseEntity<?> listarAnimais() {
        List<Animal> animais = animalBean.listarAnimais();
        if (!animais.isEmpty()) {
            List<AnimalResponseModel> animaisResponseModel = new ArrayList<>();
            for (Animal animal : animais) {
                AnimalResponseModel animalResponseModel = new AnimalResponseModel();
                animalResponseModel.setId(animal.getId());
                animalResponseModel.setFoto("");
                animalResponseModel.setCor(animal.getCor());
                animalResponseModel.setNome(animal.getNome());
                animalResponseModel.setPeso(animal.getPeso());
                animalResponseModel.setRaca(animal.getRaca());
                animalResponseModel.setCodigoChip(animal.getCodigoChip());
                animalResponseModel.setTemperamento(animal.getTemperamento());
                animalResponseModel.setDataNascimento(animal.getDataNascimento());
                animalResponseModel.setCodigoTatuagem(animal.getCodigoTatuagem());
                animalResponseModel.setNomeCientifico(animal.getNomeCientifico());
                animalResponseModel.setNomeEspecie(animal.getNomeEspecie());
                animalResponseModel.setTamanhoPorte(animal.getTamanhoPorte());
                animaisResponseModel.add(animalResponseModel);
            }
            return new ResponseEntity<>(animaisResponseModel, HttpStatus.OK);
        }
        return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Remove um animal", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping
    public ResponseEntity<?> remover(
            @RequestParam(value = "id", required = false) Long animalId
    ) {
        animalBean.remover(animalId);
        return new ResponseEntity<>("[]", HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um Animal", security = {@SecurityRequirement(name = "token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema( schema = @Schema(implementation = AnimalResponseModel.class))) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizar(
            @Valid @org.springframework.web.bind.annotation.RequestBody AnimalViewModel animalViewModel,
            @RequestParam(value = "id", required = true) Long animalId) {

        Animal animalCadastrado = animalBean.atualizar(animalViewModel, animalId);
        if (animalCadastrado != null) {
            AnimalResponseModel animalResponseModel = new AnimalResponseModel();
            animalResponseModel.setId(animalCadastrado.getId());
            animalResponseModel.setFoto("");
            animalResponseModel.setCor(animalCadastrado.getCor());
            animalResponseModel.setNome(animalCadastrado.getNome());
            animalResponseModel.setPeso(animalCadastrado.getPeso());
            animalResponseModel.setRaca(animalCadastrado.getRaca());
            animalResponseModel.setCodigoChip(animalCadastrado.getCodigoChip());
            animalResponseModel.setTemperamento(animalCadastrado.getTemperamento());
            animalResponseModel.setDataNascimento(animalCadastrado.getDataNascimento());
            animalResponseModel.setCodigoTatuagem(animalCadastrado.getCodigoTatuagem());
            animalResponseModel.setNomeCientifico(animalCadastrado.getNomeCientifico());
            animalResponseModel.setNomeEspecie(animalCadastrado.getNomeEspecie());
            animalResponseModel.setTamanhoPorte(animalCadastrado.getTamanhoPorte());
            return new ResponseEntity<>(animalResponseModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
