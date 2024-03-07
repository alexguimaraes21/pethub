CREATE SEQUENCE IF NOT EXISTS tb_email_cd_email_seq
    INCREMENT 1
    START 1;

CREATE TABLE IF NOT EXISTS public.tb_email
(
    cd_email bigint NOT NULL DEFAULT nextval('tb_email_cd_email_seq'),
    ds_email character varying(200) NOT NULL,
    tp_email character varying(50) NOT NULL,
    cd_pessoa bigint NOT NULL,
    PRIMARY KEY (cd_pessoa)
);