CREATE SEQUENCE IF NOT EXISTS tb_telefone_cd_telefone_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_telefone
(
    cd_telefone bigint NOT NULL DEFAULT nextval('tb_telefone_cd_telefone_seq'),
    nr_ddd character varying(3) NOT NULL,
    nr_telefone character varying(9) NOT NULL,
    tp_telefone character varying(50) NOT NULL,
    cd_pessoa bigint NOT NULL,
    PRIMARY KEY (cd_telefone)
);