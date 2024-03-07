ALTER TABLE IF EXISTS public.tb_vacina
    ADD CONSTRAINT fk_vacina_historico_saude FOREIGN KEY (cd_historico_saude)
        REFERENCES public.tb_historico_saude (cd_historico_saude) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;