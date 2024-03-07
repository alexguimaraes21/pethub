package br.com.fiap.petapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receita {

    public static final String TABLE_NAME = "public.tb_receita";
    
    private long id;
    private String medicamento;
    private String dosagem;
    private String periodicidade;
    private String crmvVeterinario;
    private String nomeVeterinario;
    private HistoricoSaude historicoSaude;
    
    public static class Builder {
        private long id;
        private String medicamento;
        private String dosagem;
        private String periodicidade;
        private String crmvVeterinario;
        private String nomeVeterinario;
        private HistoricoSaude historicoSaude;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setMedicamento(String medicamento) {
            this.medicamento = medicamento;
            return this;
        }

        public Builder setDosagem(String dosagem) {
            this.dosagem = dosagem;
            return this;
        }

        public Builder setPeriodicidade(String periodicidade) {
            this.periodicidade = periodicidade;
            return this;
        }

        public Builder setCrmvVeterinario(String crmvVeterinario) {
            this.crmvVeterinario = crmvVeterinario;
            return this;
        }

        public Builder setNomeVeterinario(String nomeVeterinario) {
            this.nomeVeterinario = nomeVeterinario;
            return this;
        }

        public Builder setHistoricoSaude(HistoricoSaude historicoSaude) {
            this.historicoSaude = historicoSaude;
            return this;
        }

        public Receita build() {
            return new Receita(this.id, this.medicamento, this.dosagem, this.periodicidade, this.crmvVeterinario,
                    this.nomeVeterinario, this.historicoSaude);
        }
    }
}
