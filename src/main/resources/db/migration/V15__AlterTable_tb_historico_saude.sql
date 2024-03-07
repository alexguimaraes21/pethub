ALTER TABLE IF EXISTS public.tb_historico_saude
    ADD CONSTRAINT fk_historico_saude_animal FOREIGN KEY (cd_animal)
        REFERENCES public.tb_animal (cd_animal) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;