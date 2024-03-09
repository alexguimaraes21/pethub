package br.com.fiap.petapi.beans;

import br.com.fiap.petapi.models.Animal;
import br.com.fiap.petapi.models.HistoricoSaude;
import br.com.fiap.petapi.repositories.AnimalRepository;
import br.com.fiap.petapi.repositories.HistoricoSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class HistoricoSaudeBean {

    HistoricoSaudeRepository historicoSaudeRepository;
    AnimalRepository animalRepository;

    @Autowired
    public HistoricoSaudeBean(HistoricoSaudeRepository historicoSaudeRepository,
                              AnimalRepository animalRepository) {
        this.historicoSaudeRepository = historicoSaudeRepository;
        this.animalRepository = animalRepository;
    }

    public HistoricoSaude cadastrar(long animalId) {
        Optional<Animal> animal = animalRepository.buscarPorId(animalId);
        if (animal.isPresent()) {
            HistoricoSaude historicoSaude = new HistoricoSaude.Builder()
                    .setAnimal(animal.get())
                    .setCadastradoEm(LocalDateTime.now()).build();
            return historicoSaudeRepository.cadastrar(historicoSaude);
        }
        return null;
    }

    public Optional<HistoricoSaude> buscarPorId(long historicoId, long animalId) {
        Optional<Animal> animal = animalRepository.buscarPorId(animalId);
        if (animal.isPresent()) {
            return historicoSaudeRepository.buscarPorId(historicoId, animal.get().getId());
        }
        return Optional.empty();
    }

    public List<HistoricoSaude> listarHistoricoSaude(long animalId) {
        Optional<Animal> animal = animalRepository.buscarPorId(animalId);
        if (animal.isPresent()) {
            return historicoSaudeRepository.listarTodos(animal.get().getId());
        }
        return new ArrayList<>();
    }
}
