package br.com.fiap.petapi.beans;

import br.com.fiap.petapi.models.Pessoa;
import br.com.fiap.petapi.models.Usuario;
import br.com.fiap.petapi.repositories.PessoaRepository;
import br.com.fiap.petapi.repositories.UsuarioRepository;
import br.com.fiap.petapi.viewmodels.PessoaViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioBean {

    UsuarioRepository usuarioRepository;
    PessoaRepository pessoaRepository;

    @Autowired
    public UsuarioBean(UsuarioRepository usuarioRepository,
                       PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastraUsuario(String usuarioId, PessoaViewModel pessoaVM) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaVM.getNome());
        pessoa.setCpf(pessoaVM.getCpf());
        pessoa.setDataNascimento(pessoaVM.getDataNascimento());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setPessoa(pessoa);

        return usuarioRepository.cadastra(usuario);
    }
}
