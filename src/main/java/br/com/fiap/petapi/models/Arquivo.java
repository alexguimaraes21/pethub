package br.com.fiap.petapi.models;

import br.com.fiap.petapi.enums.TipoArquivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Arquivo {

    public static final String TABLE_NAME = "public.tb_arquivo";

    private long id;
    private double tamanho;
    private String nomeArquivo;
    private TipoArquivo tipo;
    private String subpasta;
    private LocalDateTime cadastradoEm;

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", tamanho=" + tamanho +
                ", nomeArquivo='" + nomeArquivo + '\'' +
                ", tipo=" + tipo +
                ", subpasta='" + subpasta + '\'' +
                ", cadastradoEm=" + cadastradoEm +
                '}';
    }

    public static class Builder {
        private long id;
        private double tamanho;
        private String nomeArquivo;
        private TipoArquivo tipo;
        private String subpasta;
        private LocalDateTime cadastradoEm;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTamanho(double tamanho) {
            this.tamanho = tamanho;
            return this;
        }

        public Builder setNomeArquivo(String nomeArquivo) {
            this.nomeArquivo = nomeArquivo;
            return this;
        }

        public Builder setTipo(TipoArquivo tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder setSubpasta(String subpasta) {
            this.subpasta = subpasta;
            return this;
        }

        public Builder setCadastradoEm(LocalDateTime cadastradoEm) {
            this.cadastradoEm = cadastradoEm;
            return this;
        }

        public Arquivo build() {
            return new Arquivo(this.id, this.tamanho, this.nomeArquivo, this.tipo, this.subpasta, this.cadastradoEm);
        }
    }
}
