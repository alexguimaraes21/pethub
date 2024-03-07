CREATE SEQUENCE IF NOT EXISTS tb_pessoa_cd_pessoa_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_pessoa
(
    cd_pessoa bigint NOT NULL DEFAULT nextval('tb_pessoa_cd_pessoa_seq'),
    nm_pessoa character varying(255) NOT NULL,
    nr_cpf character varying(11) NOT NULL,
    dt_nascimento date NOT NULL,
    cd_arquivo bigint,
    PRIMARY KEY (cd_pessoa)
);