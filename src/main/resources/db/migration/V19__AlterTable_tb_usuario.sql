ALTER TABLE IF EXISTS public.tb_usuario
    ADD CONSTRAINT fk_usuario_pessoa FOREIGN KEY (cd_pessoa)
        REFERENCES public.tb_pessoa (cd_pessoa) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;