package br.com.fiap.petapi.beans;

import br.com.fiap.petapi.models.Animal;
import br.com.fiap.petapi.models.HistoricoSaude;
import br.com.fiap.petapi.models.Receita;
import br.com.fiap.petapi.models.Vacina;
import br.com.fiap.petapi.repositories.AnimalRepository;
import br.com.fiap.petapi.repositories.HistoricoSaudeRepository;
import br.com.fiap.petapi.repositories.ReceitaRepository;
import br.com.fiap.petapi.repositories.VacinaRepository;
import br.com.fiap.petapi.responsemodel.HistoricoSaudeResponseModel;
import br.com.fiap.petapi.services.HistoricoSaudeValidatorService;
import br.com.fiap.petapi.viewmodels.HistoricoSaudeViewModel;
import br.com.fiap.petapi.viewmodels.ReceitaViewModel;
import br.com.fiap.petapi.viewmodels.VacinaViewModel;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class HistoricoSaudeBean {

    HistoricoSaudeRepository historicoSaudeRepository;
    AnimalRepository animalRepository;
    VacinaRepository vacinaRepository;
    ReceitaRepository receitaRepository;
    HistoricoSaudeValidatorService historicoSaudeValidatorService;

    @Autowired
    public HistoricoSaudeBean(HistoricoSaudeRepository historicoSaudeRepository,
                              AnimalRepository animalRepository,
                              VacinaRepository vacinaRepository,
                              ReceitaRepository receitaRepository,
                              HistoricoSaudeValidatorService historicoSaudeValidatorService) {
        this.historicoSaudeRepository = historicoSaudeRepository;
        this.animalRepository = animalRepository;
        this.vacinaRepository = vacinaRepository;
        this.receitaRepository = receitaRepository;
        this.historicoSaudeValidatorService = historicoSaudeValidatorService;
    }

    public HistoricoSaudeResponseModel cadastrar(long animalId, HistoricoSaudeViewModel historicoSaudeViewModel) throws ConstraintViolationException {
        Optional<Animal> animal = animalRepository.buscarPorId(animalId);
        if (animal.isPresent()) {
            HistoricoSaude historicoSaude = new HistoricoSaude.Builder()
                    .setAnimal(animal.get())
                    .setCadastradoEm(LocalDateTime.now()).build();
            HistoricoSaudeResponseModel historicoSaudeResponseModel = new HistoricoSaudeResponseModel();
            HistoricoSaude historicoSaudeCadastrado = null;
            switch (historicoSaudeViewModel.getTipo()) {
                case VACINA:
                    VacinaViewModel vacinaViewModel = new VacinaViewModel();
                    vacinaViewModel.setDataVacina(historicoSaudeViewModel.getDataVacina());
                    vacinaViewModel.setDescricaoVacina(historicoSaudeViewModel.getDescricaoVacina());
                    this.historicoSaudeValidatorService.validaVacina(vacinaViewModel);
                    historicoSaudeCadastrado = historicoSaudeRepository.cadastrar(historicoSaude);
                    Vacina vacinaCadastrada = this.cadastrarVacina(vacinaViewModel,animal.get().getId(), historicoSaudeCadastrado);
                    historicoSaudeResponseModel.setId(historicoSaudeCadastrado.getId());
                    historicoSaudeResponseModel.setDescricaoVacina(vacinaCadastrada.getDescricao());
                    historicoSaudeResponseModel.setDataVacina(vacinaCadastrada.getData());
                    return historicoSaudeResponseModel;
                case CONSULTA:
                    ReceitaViewModel receitaViewModel = new ReceitaViewModel();
                    receitaViewModel.setNomeVeterinario(historicoSaudeViewModel.getNomeVeterinario());
                    receitaViewModel.setPeriodicidade(historicoSaudeViewModel.getPeriodicidade());
                    receitaViewModel.setMedicamento(historicoSaudeViewModel.getMedicamento());
                    receitaViewModel.setDosagem(historicoSaudeViewModel.getDosagem());
                    receitaViewModel.setCrmvVeterinario(historicoSaudeViewModel.getCrmvVeterinario());
                    this.historicoSaudeValidatorService.validaReceita(receitaViewModel);
                    historicoSaudeCadastrado = historicoSaudeRepository.cadastrar(historicoSaude);
                    Receita receitaCadastrada = this.cadastrarReceita(receitaViewModel, animal.get().getId(), historicoSaudeCadastrado);
                    historicoSaudeResponseModel.setId(historicoSaudeCadastrado.getId());
                    historicoSaudeResponseModel.setNomeVeterinario(receitaCadastrada.getNomeVeterinario());
                    historicoSaudeResponseModel.setDosagem(receitaViewModel.getDosagem());
                    historicoSaudeResponseModel.setCrmvVeterinario(receitaViewModel.getCrmvVeterinario());
                    historicoSaudeResponseModel.setPeriodicidade(receitaViewModel.getPeriodicidade());
                    historicoSaudeResponseModel.setMedicamento(receitaViewModel.getMedicamento());
                    return historicoSaudeResponseModel;
            }
            return null;
        }
        return null;
    }

    public HistoricoSaudeResponseModel buscarPorId(long historicoId, long animalId) {
        Optional<Animal> animal = animalRepository.buscarPorId(animalId);
        if (animal.isPresent()) {
            HistoricoSaudeResponseModel historicoSaudeResponseModel = new HistoricoSaudeResponseModel();
            Optional<HistoricoSaude> historicoSaude = historicoSaudeRepository.buscarPorId(historicoId, animal.get().getId());
            if (historicoSaude.isPresent()) {
                historicoSaudeResponseModel.setId(historicoSaude.get().getId());
                historicoSaudeResponseModel.setCadastradoEm(historicoSaude.get().getCadastradoEm());
                Optional<Vacina> vacina = vacinaRepository.buscarPorHistoricoSaudeId(historicoSaude.get().getId());
                if (vacina.isPresent()) {
                    historicoSaudeResponseModel.setDataVacina(vacina.get().getData());
                    historicoSaudeResponseModel.setDescricaoVacina(vacina.get().getDescricao());
                }
                Optional<Receita> receita = receitaRepository.buscarPorHistoricoSaudeId(historicoSaude.get().getId());
                if (receita.isPresent()) {
                    historicoSaudeResponseModel.setNomeVeterinario(receita.get().getNomeVeterinario());
                    historicoSaudeResponseModel.setDosagem(receita.get().getDosagem());
                    historicoSaudeResponseModel.setMedicamento(receita.get().getMedicamento());
                    historicoSaudeResponseModel.setPeriodicidade(receita.get().getPeriodicidade());
                    historicoSaudeResponseModel.setCrmvVeterinario(receita.get().getCrmvVeterinario());
                }
                return historicoSaudeResponseModel;
            }
            return null;
        }
        return null;
    }

    public List<HistoricoSaudeResponseModel> listarHistoricoSaude(long animalId) {
        Optional<Animal> animal = animalRepository.buscarPorId(animalId);
        if (animal.isPresent()) {
            List<HistoricoSaudeResponseModel> listaHistoricoSaudeResponseModel = new ArrayList<>();
            for(HistoricoSaude historicoSaude : historicoSaudeRepository.listarTodos(animal.get().getId())) {
                HistoricoSaudeResponseModel historicoSaudeResponseModel = new HistoricoSaudeResponseModel();
                historicoSaudeResponseModel.setId(historicoSaude.getId());
                historicoSaudeResponseModel.setCadastradoEm(historicoSaude.getCadastradoEm());
                Optional<Vacina> vacina = vacinaRepository.buscarPorHistoricoSaudeId(historicoSaude.getId());
                if (vacina.isPresent()) {
                    historicoSaudeResponseModel.setDataVacina(vacina.get().getData());
                    historicoSaudeResponseModel.setDescricaoVacina(vacina.get().getDescricao());
                }
                Optional<Receita> receita = receitaRepository.buscarPorHistoricoSaudeId(historicoSaude.getId());
                if (receita.isPresent()) {
                    historicoSaudeResponseModel.setNomeVeterinario(receita.get().getNomeVeterinario());
                    historicoSaudeResponseModel.setDosagem(receita.get().getDosagem());
                    historicoSaudeResponseModel.setMedicamento(receita.get().getMedicamento());
                    historicoSaudeResponseModel.setPeriodicidade(receita.get().getPeriodicidade());
                    historicoSaudeResponseModel.setCrmvVeterinario(receita.get().getCrmvVeterinario());
                }
                listaHistoricoSaudeResponseModel.add(historicoSaudeResponseModel);
            }
            return listaHistoricoSaudeResponseModel;
        }
        return new ArrayList<>();
    }

    public Vacina cadastrarVacina(VacinaViewModel vacinaViewModel, long animalId, HistoricoSaude historicoSaude) {
        Optional<Animal> animal = animalRepository.buscarPorId(animalId);
        if (animal.isPresent()) {
            Vacina vacina = new Vacina.Builder()
                    .setData(vacinaViewModel.getDataVacina())
                    .setDescricao(vacinaViewModel.getDescricaoVacina())
                    .setHistoricoSaude(historicoSaude)
                    .build();
            return vacinaRepository.cadastrar(vacina);
        }
        return null;
    }

    public Receita cadastrarReceita(ReceitaViewModel receitaViewModel, long animalId, HistoricoSaude historicoSaude) {
        Optional<Animal> animal = animalRepository.buscarPorId(animalId);
        if (animal.isPresent()) {
            Receita receita = new Receita.Builder()
                    .setHistoricoSaude(historicoSaude)
                    .setCrmvVeterinario(receitaViewModel.getCrmvVeterinario())
                    .setDosagem(receitaViewModel.getDosagem())
                    .setMedicamento(receitaViewModel.getMedicamento())
                    .setPeriodicidade(receitaViewModel.getPeriodicidade())
                    .setNomeVeterinario(receitaViewModel.getNomeVeterinario())
                    .build();
            return receitaRepository.cadastrar(receita);
        }
        return null;
    }

    public boolean remover(long historicoSaudeId, long animalId) {
        HistoricoSaudeResponseModel historicoSaude = this.buscarPorId(historicoSaudeId, animalId);
        if (historicoSaude != null) {
            this.vacinaRepository.remover(historicoSaude.getId());
            this.receitaRepository.remover(historicoSaude.getId());
            this.historicoSaudeRepository.remover(historicoSaude.getId(), animalId);
            return true;
        }
        return false;
    }
}
