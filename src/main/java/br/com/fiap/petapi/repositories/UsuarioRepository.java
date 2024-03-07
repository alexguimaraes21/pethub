package br.com.fiap.petapi.repositories;

import br.com.fiap.petapi.models.Arquivo;
import br.com.fiap.petapi.models.Pessoa;
import br.com.fiap.petapi.models.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Repository
public class UsuarioRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JdbcTemplate jdbcTemplate;
    PessoaRepository pessoaRepository;

    @Autowired
    public UsuarioRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                             JdbcTemplate jdbcTemplate,
                             PessoaRepository pessoaRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.pessoaRepository = pessoaRepository;
    }

    public Usuario cadastra(Usuario usuario) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        Pessoa pessoa = pessoaRepository.cadastrar(usuario.getPessoa());
        params.addValue("id", usuario.getId());
        params.addValue("pessoa", pessoa.getId());

        String sql = "INSERT INTO " + Usuario.TABLE_NAME + " (cd_usuario, cd_pessoa) VALUES (:id, :pessoa)";

        try {
            namedParameterJdbcTemplate.update(sql, params);
            usuario.getPessoa().setId(pessoa.getId());
        } catch (DataAccessException e) {
            log.error("Erro ao salvar o arquivo no banco de dados.", e);
        }
        return usuario;
    }

    public Optional<Usuario> buscarPorId(String id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);

        String sql = "SELECT USU.cd_usuario, PES.cd_pessoa, PES.nm_pessoa, PES.nr_cpf, PES.dt_nascimento, PES.cd_arquivo" +
                " FROM " + Usuario.TABLE_NAME + " USU " +
                " INNER JOIN " + Pessoa.TABLE_NAME + " PES ON PES.cd_pessoa = USU.cd_pessoa" +
                " WHERE USU.cd_usuario = :id";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    param,
                    (rs, rowNum) -> Optional.of(new Usuario.Builder()
                            .setId(rs.getString("cd_usuario"))
                            .setPessoa(new Pessoa.Builder()
                                    .setId(rs.getLong("cd_pessoa"))
                                    .setNome(rs.getString("nm_pessoa"))
                                    .setCpf(rs.getString("nr_cpf"))
                                    .setDataNascimento(rs.getObject("dt_nascimento", LocalDate.class))
                                    .setFoto(new Arquivo.Builder()
                                            .setId(rs.getLong("cd_arquivo"))
                                            .build()
                                    ).build()
                            ).build())
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int remover(String id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        String sql = "DELETE FROM " + Usuario.TABLE_NAME + " WHERE cd_usuario = :id";
        return namedParameterJdbcTemplate.update(sql, param);
    }
}
