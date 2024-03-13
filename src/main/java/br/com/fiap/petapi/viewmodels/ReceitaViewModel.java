package br.com.fiap.petapi.viewmodels;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaViewModel {

    @Valid
    @NotNull
    @Size(min = 3, max = 255, message = "O campo Medicamento deve ter no mínimo {min} e no máximo {max} caracteres.")
    private String medicamento;

    @Valid
    @NotNull
    @Size(min = 3, max = 255, message = "O campo Dosagem deve ter no mínimo {min} e no máximo {max} caracteres.")
    private String dosagem;

    @Valid
    @NotNull
    @Size(min = 3, max = 255, message = "O campo Periodicidade deve ter no mínimo {min} e no máximo {max} caracteres.")
    private String periodicidade;

    @Valid
    @NotNull
    @Size(min = 3, max = 10, message = "O campo CRMV do Veterinário deve ter no mínimo {min} e no máximo {max} caracteres.")
    private String crmvVeterinario;

    @Valid
    @NotNull
    @Size(min = 3, max = 255, message = "O campo Nome do Veterinário deve ter no mínimo {min} e no máximo {max} caracteres.")
    private String nomeVeterinario;
}
