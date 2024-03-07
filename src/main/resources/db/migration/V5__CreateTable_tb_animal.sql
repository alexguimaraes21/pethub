CREATE SEQUENCE IF NOT EXISTS tb_animal_cd_animal_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_animal
(
    cd_animal bigint NOT NULL DEFAULT nextval('tb_animal_cd_animal_seq'),
    nm_animal character varying(150) NOT NULL,
    nm_cientifico character varying(255),
    nm_especie character varying(255) NOT NULL,
    ds_cor character varying(50) NOT NULL,
    ds_chip character varying(150),
    ds_tatuagem character varying(150),
    dt_nascimento date NOT NULL,
    ds_tamanho character varying(80) NOT NULL,
    nr_peso double precision NOT NULL,
    ds_temperamento character varying(150) NOT NULL,
    cd_pessoa bigint NOT NULL,
    ds_raca character varying(150) NOT NULL,
    cd_arquivo bigint,
    PRIMARY KEY (cd_animal)
);