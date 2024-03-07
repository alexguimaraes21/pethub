package br.com.fiap.petapi.models;

import br.com.fiap.petapi.enums.TipoEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    public static final String TABLE_NAME = "public.tb_email";

    private long id;
    private String email;
    private TipoEmail tipoEmail;
    private Pessoa pessoa;

    public static class Builder {

        private long id;
        private String email;
        private TipoEmail tipoEmail;
        private Pessoa pessoa;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setTipoEmail(TipoEmail tipoEmail) {
            this.tipoEmail = tipoEmail;
            return this;
        }

        public Builder setPessoa(Pessoa pessoa) {
            this.pessoa = pessoa;
            return this;
        }

        public Email build() {
            return new Email(this.id, this.email, this.tipoEmail, this.pessoa);
        }
    }
}
