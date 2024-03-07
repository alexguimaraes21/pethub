CREATE SEQUENCE IF NOT EXISTS tb_receita_cd_receita_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_receita
(
    cd_receita bigint NOT NULL DEFAULT nextval('tb_receita_cd_receita_seq'),
    nm_remedio character varying(255) NOT NULL,
    ds_dosagem character varying(255) NOT NULL,
    ds_periodicidade character varying(255) NOT NULL,
    nr_crmv character varying(20) NOT NULL,
    nm_veterinario character varying(255) NOT NULL,
    cd_historico_saude bigint NOT NULL,
    PRIMARY KEY (cd_receita)
);