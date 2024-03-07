package br.com.fiap.petapi.beans;

import br.com.fiap.petapi.models.Animal;
import br.com.fiap.petapi.repositories.AnimalRepository;
import br.com.fiap.petapi.services.UsuarioService;
import br.com.fiap.petapi.viewmodels.AnimalViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AnimalBean {

    AnimalRepository animalRepository;
    UsuarioService usuarioService;

    @Autowired
    public AnimalBean(AnimalRepository animalRepository,
                      UsuarioService usuarioService) {
        this.animalRepository = animalRepository;
        this.usuarioService = usuarioService;
    }

    public Animal cadastrar(AnimalViewModel animalViewModel) {
        Animal novoAnimal = new Animal.Builder()
                .setNome(animalViewModel.getNome())
                .setNomeEspecie(animalViewModel.getNomeEspecie())
                .setNomeCientifico(animalViewModel.getNomeCientifico())
                .setCor(animalViewModel.getCor())
                .setCodigoChip(animalViewModel.getCodigoChip())
                .setCodigoTatuagem(animalViewModel.getCodigoTatuagem())
                .setDataNascimento(animalViewModel.getDataNascimento())
                .setTamanhoPorte(animalViewModel.getTamanhoPorte())
                .setPeso(animalViewModel.getPeso())
                .setTemperamento(animalViewModel.getTemperamento())
                .setRaca(animalViewModel.getRaca())
                .setPessoa(usuarioService.retornaUsuarioAutenticado().get().getPessoa())
                .build();
        return this.animalRepository.cadastrar(novoAnimal);
    }

    public List<Animal> listarAnimais() {
        return this.animalRepository.listarTodos();
    }

    public Optional<Animal> buscarPorId(long id) {
        return this.animalRepository.buscarPorId(id);
    }

    public void remover(long id) {
        this.animalRepository.remover(id);
    }
}
