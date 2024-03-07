package br.com.fiap.petapi.models;

import br.com.fiap.petapi.enums.TipoEndereco;
import br.com.fiap.petapi.enums.TipoLogradouro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    public static final String TABLE_NAME = "public.tb_endereco";

    private long id;
    private String logradouro;
    private TipoLogradouro tipoLogradouro;
    private String numeroLogradouro;
    private String complemento;
    private TipoEndereco tipoEndereco;
    private Pessoa pessoa;

    public static class Builder {
        private long id;
        private String logradouro;
        private TipoLogradouro tipoLogradouro;
        private String numeroLogradouro;
        private String complemento;
        private TipoEndereco tipoEndereco;
        private Pessoa pessoa;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setLogradouro(String logradouro) {
            this.logradouro = logradouro;
            return this;
        }

        public Builder setTipoLogradouro(TipoLogradouro tipoLogradouro) {
            this.tipoLogradouro = tipoLogradouro;
            return this;
        }

        public Builder setNumeroLogradouro(String numeroLogradouro) {
            this.numeroLogradouro = numeroLogradouro;
            return this;
        }

        public Builder setComplemento(String complemento) {
            this.complemento = complemento;
            return this;
        }

        public Builder setTipoEndereco(TipoEndereco tipoEndereco) {
            this.tipoEndereco = tipoEndereco;
            return this;
        }

        public Builder setPessoa(Pessoa pessoa) {
            this.pessoa = pessoa;
            return this;
        }

        public Endereco build() {
            return new Endereco(this.id, this.logradouro, this.tipoLogradouro, this.numeroLogradouro, this.complemento,
                    this.tipoEndereco, this.pessoa);
        }
    }
}
