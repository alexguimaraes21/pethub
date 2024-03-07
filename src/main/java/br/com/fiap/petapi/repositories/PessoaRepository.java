package br.com.fiap.petapi.repositories;

import br.com.fiap.petapi.enums.TipoArquivo;
import br.com.fiap.petapi.models.Arquivo;
import br.com.fiap.petapi.models.Pessoa;
import br.com.fiap.petapi.models.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class PessoaRepository extends AbstractRepository<Pessoa> {

    @Autowired
    public PessoaRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Pessoa cadastrar(Pessoa pessoa) {
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nome", pessoa.getNome());
        params.addValue("cpf", pessoa.getCpf());
        params.addValue("dataNascimento", pessoa.getDataNascimento());

        String sql = "";
        if (pessoa.getFoto() != null) {
            params.addValue("foto", pessoa.getFoto().getId());
            sql = "INSERT INTO " + Pessoa.TABLE_NAME + " (nm_pessoa, nr_cpf, dt_nascimento, cd_arquivo) " +
                    "VALUES (:nome, :cpf, :dataNascimento, :foto)";
        } else {
            sql = "INSERT INTO " + Pessoa.TABLE_NAME + " (nm_pessoa, nr_cpf, dt_nascimento) " +
                    "VALUES (:nome, :cpf, :dataNascimento)";
        }

        try {
            namedParameterJdbcTemplate.update(sql, params, holder, new String[] {"cd_pessoa"});
            pessoa.setId(Objects.requireNonNull(holder.getKey()).longValue());
        } catch (DataAccessException e) {
            log.error("Erro ao salvar o arquivo no banco de dados.", e);
        }
        return pessoa;
    }

    @Override
    public Optional<Pessoa> buscarPorId(long id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);

        String sql = "SELECT cd_pessoa, nm_pessoa, nr_cpf, dt_nascimento, cd_arquivo" +
                " FROM " + Pessoa.TABLE_NAME + " WHERE cd_pessoa = :id";

        return this.namedParameterJdbcTemplate.queryForObject(
                sql,
                param,
                (rs, rowNum) -> Optional.of(new Pessoa.Builder()
                        .setId(rs.getLong("cd_pessoa"))
                        .setNome(rs.getString("nm_pessoa"))
                        .setCpf(rs.getString("nr_cpf"))
                        .setDataNascimento(rs.getObject("dt_nascimento", LocalDate.class))
                        .setFoto(new Arquivo.Builder()
                                .setId(rs.getLong("cd_arquivo"))
                                .build()
                        ).build())
        );
    }

    @Override
    public List<Pessoa> listarTodos() {
        return new ArrayList<>();
    }

    @Override
    public int remover(long id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        String sql = "DELETE FROM " + Pessoa.TABLE_NAME + " WHERE cd_pessoa = :id";
        return namedParameterJdbcTemplate.update(sql, param);
    }
}
