CREATE SEQUENCE IF NOT EXISTS tb_historico_saude_cd_historico_saude_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_historico_saude
(
    cd_historico_saude bigint NOT NULL DEFAULT nextval('tb_historico_saude_cd_historico_saude_seq'),
    dt_cadastro timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cd_animal bigint NOT NULL,
    PRIMARY KEY (cd_historico_saude)
);