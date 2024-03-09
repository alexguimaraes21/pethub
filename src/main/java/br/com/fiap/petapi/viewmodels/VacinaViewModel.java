package br.com.fiap.petapi.viewmodels;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacinaViewModel {

    @Valid
    @PastOrPresent
    private LocalDate dataVacina;

    @Valid
    @Size(min = 3, max = 5000, message = "O campo Descricao da Vacina não pode ter menos que {min} e mais que {max} caracteres.")
    private String descricaoVacina;
}
