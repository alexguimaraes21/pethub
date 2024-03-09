package br.com.fiap.petapi.viewmodels;

import br.com.fiap.petapi.models.Arquivo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaViewModel {

    @Valid
    @NotNull
    @Size(min = 10, max = 255, message = "O campo Nome não pode ter menos que {min} e mais que {max} caracteres.")
    private String nome;

    @Valid
    @NotNull
    @CPF
    private String cpf;

    @Valid
    @NotNull
    @Past
    private LocalDate dataNascimento;

    private MultipartFile foto;
}
