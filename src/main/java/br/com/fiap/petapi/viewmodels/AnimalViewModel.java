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
    @NotNull(message = "Campo Nome não deve ser nulo")
    @Size(min = 3, max = 100, message = "Campo Nome deve ter no mínimo {min} e no máximo {max} caracteres")
    private String nome;

    private String nomeCientifico;

    @Valid
    @NotNull(message = "Campo Espécie não deve ser nulo")
    @Size(min = 3, max = 100, message = "Campo Espécie deve ter no mínimo {min} e no máximo {max} caracteres")
    private String nomeEspecie;

    @Valid
    @Size(min = 3, max = 100, message = "Campo Cor deve ter no mínimo {min} e no máximo {max} caracteres")
    private String cor;

    private String codigoChip;

    private String codigoTatuagem;

    @Valid
    @PastOrPresent(message = "Campo Data de Nascimento não pode estar no futuro")
    private LocalDate dataNascimento;

    @Valid
    @Size(min = 3, max = 100, message = "Campo Porte deve ter no mínimo {min} e máximo {max} caracteres")
    private String tamanhoPorte;

    @Valid
    @Positive(message = "Campo Peso deve ser um valor positivo (Maior que zero)")
    private double peso;

    @Valid
    @Size(min = 3, max = 100, message = "Campo Temperamento deve ter no mínimo {min} e máximo {max} caracteres")
    private String temperamento;

    @Valid
    @Size(min = 3, max = 100, message = "Campo Raça deve ter no mínimo {min} e máximo {max} caracteres")
    private String raca;

    private MultipartFile foto;
}
