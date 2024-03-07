package br.com.fiap.petapi.models;

import br.com.fiap.petapi.enums.TipoTelefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

    public static final String TABLE_NAME = "public.tb_telefone";

    private long id;
    private String ddd;
    private String telefone;
    private TipoTelefone tipoTelefone;
    private Pessoa pessoa;

    public static class Builder {
        private long id;
        private String ddd;
        private String telefone;
        private TipoTelefone tipoTelefone;
        private Pessoa pessoa;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setDdd(String ddd) {
            this.ddd = ddd;
            return this;
        }

        public Builder setTelefone(String telefone) {
            this.telefone = telefone;
            return this;
        }

        public Builder setTipoTelefone(TipoTelefone tipoTelefone) {
            this.tipoTelefone = tipoTelefone;
            return this;
        }

        public Builder setPessoa(Pessoa pessoa) {
            this.pessoa = pessoa;
            return this;
        }

        public Telefone build() {
            return new Telefone(this.id, this.ddd, this.telefone, this.tipoTelefone, this.pessoa);
        }
    }
}
