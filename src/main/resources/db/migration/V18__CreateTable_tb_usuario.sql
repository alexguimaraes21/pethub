CREATE TABLE IF NOT EXISTS public.tb_usuario
(
    cd_usuario character varying(100) NOT NULL,
    cd_pessoa bigint NOT NULL,
    PRIMARY KEY (cd_usuario, cd_pessoa)
);