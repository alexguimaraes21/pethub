package br.com.fiap.petapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    public static final String TABLE_NAME = "public.tb_pessoa";

    private long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private Arquivo foto;

    public static class Builder {
        private long id;
        private String nome;
        private String cpf;
        private LocalDate dataNascimento;
        private Arquivo foto;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder setDataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
            return this;
        }

        public Builder setFoto(Arquivo foto) {
            this.foto = foto;
            return this;
        }

        public Pessoa build() {
            return new Pessoa(this.id, this.nome, this.cpf, this.dataNascimento, this.foto);
        }
    }
}
