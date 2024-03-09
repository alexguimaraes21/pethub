package br.com.fiap.petapi.responsemodel;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseModel {

    private String id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String foto;
}
