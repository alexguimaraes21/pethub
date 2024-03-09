package br.com.fiap.petapi.responsemodel;

import br.com.fiap.petapi.models.Arquivo;
import br.com.fiap.petapi.models.Pessoa;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponseModel {

    private long id;
    private String nome;
    private String nomeCientifico;
    private String nomeEspecie;
    private String cor;
    private String codigoChip;
    private String codigoTatuagem;
    private LocalDate dataNascimento;
    private String tamanhoPorte;
    private double peso;
    private String temperamento;
    private String raca;
    private String foto;
}
