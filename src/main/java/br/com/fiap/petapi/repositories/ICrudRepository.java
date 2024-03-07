package br.com.fiap.petapi.repositories;

import java.util.List;
import java.util.Optional;

public interface ICrudRepository<T> {

    T cadastrar(T t);
    Optional<T> buscarPorId(long id);
    List<T> listarTodos();
    int remover(long id);
}
