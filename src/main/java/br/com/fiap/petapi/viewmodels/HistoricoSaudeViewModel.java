package br.com.fiap.petapi.viewmodels;

import br.com.fiap.petapi.enums.TipoHistoricoSaude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoSaudeViewModel {

    @Valid
    @NotNull
    private TipoHistoricoSaude tipo;

    private String medicamento;

    private String dosagem;

    private String periodicidade;

    private String crmvVeterinario;

    private String nomeVeterinario;

    private LocalDate dataVacina;

    private String descricaoVacina;
}
