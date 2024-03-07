CREATE SEQUENCE IF NOT EXISTS tb_vacina_cd_vacina_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_vacina
(
    cd_vacina bigint NOT NULL DEFAULT nextval('tb_vacina_cd_vacina_seq'),
    dt_vacina date NOT NULL,
    ds_vacina character varying(255) NOT NULL,
    cd_historico_saude bigint NOT NULL,
    PRIMARY KEY (cd_vacina)
);
