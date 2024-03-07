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
public class Animal {

    public static final String TABLE_NAME = "public.tb_animal";
    
    private long id;
    private String nome;
    private String nomeCientifico;
    private String nomeEspecie;
    private String cor;
    private String codigoChip;
    private String codigoTatuagem;
    private LocalDate dataNascimento;
    private String tamanhoPorte;
    private double peso;
    private String temperamento;
    private String raca;
    private Pessoa pessoa;
    private Arquivo foto;
    
    public static class Builder {
        private long id;
        private String nome;
        private String nomeCientifico;
        private String nomeEspecie;
        private String cor;
        private String codigoChip;
        private String codigoTatuagem;
        private LocalDate dataNascimento;
        private String tamanhoPorte;
        private double peso;
        private String temperamento;
        private String raca;
        private Pessoa pessoa;
        private Arquivo foto;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setNomeCientifico(String nomeCientifico) {
            this.nomeCientifico = nomeCientifico;
            return this;
        }

        public Builder setNomeEspecie(String nomeEspecie) {
            this.nomeEspecie = nomeEspecie;
            return this;
        }

        public Builder setCor(String cor) {
            this.cor = cor;
            return this;
        }

        public Builder setCodigoChip(String codigoChip) {
            this.codigoChip = codigoChip;
            return this;
        }

        public Builder setCodigoTatuagem(String codigoTatuagem) {
            this.codigoTatuagem = codigoTatuagem;
            return this;
        }

        public Builder setDataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
            return this;
        }

        public Builder setTamanhoPorte(String tamanhoPorte) {
            this.tamanhoPorte = tamanhoPorte;
            return this;
        }

        public Builder setPeso(double peso) {
            this.peso = peso;
            return this;
        }

        public Builder setTemperamento(String temperamento) {
            this.temperamento = temperamento;
            return this;
        }

        public Builder setRaca(String raca) {
            this.raca = raca;
            return this;
        }

        public Builder setPessoa(Pessoa pessoa) {
            this.pessoa = pessoa;
            return this;
        }

        public Builder setFoto(Arquivo foto) {
            this.foto = foto;
            return this;
        }

        public Animal build() {
            return new Animal(this.id, this.nome, this.nomeCientifico, this.nomeEspecie, this.cor, this.codigoChip,
                    this.codigoTatuagem, this.dataNascimento, this.tamanhoPorte, this.peso, this.temperamento,
                    this.raca, this.pessoa, this.foto);
        }
    }
}
