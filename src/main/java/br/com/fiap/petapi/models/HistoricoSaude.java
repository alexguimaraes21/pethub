package br.com.fiap.petapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoSaude {

    public static final String TABLE_NAME = "public.tb_historico_saude";

    private long id;
    private LocalDateTime cadastradoEm;
    private Animal animal;
    
    public static class Builder {

        private long id;
        private LocalDateTime cadastradoEm;
        private Animal animal;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setCadastradoEm(LocalDateTime cadastradoEm) {
            this.cadastradoEm = cadastradoEm;
            return this;
        }

        public Builder setAnimal(Animal animal) {
            this.animal = animal;
            return this;
        }

        public HistoricoSaude build() {
            return new HistoricoSaude(this.id, this.cadastradoEm, this.animal);
        }
    }
}
