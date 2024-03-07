ALTER TABLE IF EXISTS public.tb_receita
    ADD CONSTRAINT fk_receita_historico_saude FOREIGN KEY (cd_historico_saude)
        REFERENCES public.tb_historico_saude (cd_historico_saude) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;