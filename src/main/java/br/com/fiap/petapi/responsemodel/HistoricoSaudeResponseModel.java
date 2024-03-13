package br.com.fiap.petapi.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricoSaudeResponseModel {

    private long id;
    private LocalDateTime cadastradoEm;
    private String medicamento;
    private String dosagem;
    private String periodicidade;
    private String crmvVeterinario;
    private String nomeVeterinario;
    private LocalDate dataVacina;
    private String descricaoVacina;
}
