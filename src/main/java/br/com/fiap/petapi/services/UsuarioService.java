package br.com.fiap.petapi.services;

import br.com.fiap.petapi.models.Pessoa;
import br.com.fiap.petapi.models.Usuario;
import br.com.fiap.petapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;
    StorageService storageService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          StorageService storageService) {
        this.usuarioRepository = usuarioRepository;
        this.storageService = storageService;
    }

    public Optional<Usuario> retornaUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Usuario> usuario = usuarioRepository.buscarPorId(auth.getName());
        if(usuario.isPresent()) {
            storageService.load(usuario.get().getPessoa().getFoto().getId());
            return usuario;
        }
        return Optional.empty();
    }
}
