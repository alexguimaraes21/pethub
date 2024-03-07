package br.com.fiap.petapi.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T> implements ICrudRepository<T> {

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;

    public abstract T cadastrar(T t);
    public abstract Optional<T> buscarPorId(long id);
    public abstract List<T> listarTodos();
    public abstract int remover(long id);
}
