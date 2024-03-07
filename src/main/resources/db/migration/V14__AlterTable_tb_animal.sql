ALTER TABLE IF EXISTS public.tb_animal
    ADD CONSTRAINT fk_animal_pessoa FOREIGN KEY (cd_pessoa)
        REFERENCES public.tb_pessoa (cd_pessoa) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;