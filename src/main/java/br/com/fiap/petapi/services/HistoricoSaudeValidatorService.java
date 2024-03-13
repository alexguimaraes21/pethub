package br.com.fiap.petapi.services;

import br.com.fiap.petapi.viewmodels.ReceitaViewModel;
import br.com.fiap.petapi.viewmodels.VacinaViewModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HistoricoSaudeValidatorService {

    private Validator validator;

    @Autowired
    public HistoricoSaudeValidatorService(Validator validator) {
        this.validator = validator;
    }

    public void validaVacina(VacinaViewModel vacinaViewModel) {
        Set<ConstraintViolation<VacinaViewModel>> violations = this.validator.validate(vacinaViewModel);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validaReceita(ReceitaViewModel receitaViewModel) {
        Set<ConstraintViolation<ReceitaViewModel>> violations = this.validator.validate(receitaViewModel);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
