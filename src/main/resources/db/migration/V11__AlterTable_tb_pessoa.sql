ALTER TABLE IF EXISTS public.tb_pessoa
    ADD CONSTRAINT fk_pessoa_arquivo FOREIGN KEY (cd_arquivo)
        REFERENCES public.tb_arquivo (cd_arquivo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;