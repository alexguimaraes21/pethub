package br.com.fiap.petapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacina {

    public static final String TABLE_NAME = "public.tb_vacina";
    
    private long id;
    private LocalDate data;
    private String descricao;
    private HistoricoSaude historicoSaude;
    
    public static class Builder {
        private long id;
        private LocalDate data;
        private String descricao;
        private HistoricoSaude historicoSaude;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setData(LocalDate data) {
            this.data = data;
            return this;
        }

        public Builder setDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder setHistoricoSaude(HistoricoSaude historicoSaude) {
            this.historicoSaude = historicoSaude;
            return this;
        }

        public Vacina build() {
            return new Vacina(this.id, this.data, this.descricao, this.historicoSaude);
        }
    }
}
