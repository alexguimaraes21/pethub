CREATE SEQUENCE IF NOT EXISTS tb_arquivo_cd_arquivo_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_arquivo
(
    cd_arquivo bigint NOT NULL DEFAULT nextval('tb_arquivo_cd_arquivo_seq'),
    nr_tamanho double precision NOT NULL,
    nm_original character varying(255) NOT NULL,
    ds_tipo character varying(255) NOT NULL,
    ds_subpasta character varying(255) NOT NULL,
    dt_criacao timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (cd_arquivo)
);