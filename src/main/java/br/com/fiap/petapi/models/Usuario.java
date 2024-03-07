package br.com.fiap.petapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    public static final String TABLE_NAME = "public.tb_usuario";

    private String id;
    private Pessoa pessoa;

    public static class Builder {
        private String id;
        private Pessoa pessoa;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setPessoa(Pessoa pessoa) {
            this.pessoa = pessoa;
            return this;
        }

        public Usuario build() {
            return new Usuario(this.id, this.pessoa);
        }
    }
}
