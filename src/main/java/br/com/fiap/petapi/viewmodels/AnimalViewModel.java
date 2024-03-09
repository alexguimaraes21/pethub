package br.com.fiap.petapi.viewmodels;

import br.com.fiap.petapi.models.Arquivo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnimalViewModel {

    @Valid
    @NotNull
    @Size(min = 3, max = 100)
    private String nome;

    private String nomeCientifico;

    @Valid
    @NotNull
    @Size(min = 3, max = 100)
    private String nomeEspecie;

    @Valid
    @NotNull
    @Size(min = 3, max = 100)
    private String cor;

    private String codigoChip;

    private String codigoTatuagem;

    @Valid
    @NotNull
    @PastOrPresent
    private LocalDate dataNascimento;

    @Valid
    @NotNull
    @Size(min = 3, max = 100)
    private String tamanhoPorte;

    @Valid
    @NotNull
    @Positive
    private double peso;

    @Valid
    @NotNull
    @Size(min = 3, max = 100)
    private String temperamento;

    @Valid
    @NotNull
    @Size(min = 3, max = 100)
    private String raca;

    private MultipartFile foto;
}
