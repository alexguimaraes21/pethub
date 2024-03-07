CREATE SEQUENCE IF NOT EXISTS tb_endereco_cd_endereco_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_endereco
(
    cd_endereco bigint NOT NULL DEFAULT nextval('tb_endereco_cd_endereco_seq'),
    nm_logradouro character varying(255) NOT NULL,
    tp_logradouro character varying(50) NOT NULL,
    nr_logradouro character varying(10) NOT NULL,
    ds_complemento character varying(255),
    cd_pessoa bigint NOT NULL,
    tp_endereco character varying(50) NOT NULL,
    PRIMARY KEY (cd_endereco)
);