package br.com.fiap.petapi.repositories;

import br.com.fiap.petapi.enums.TipoArquivo;
import br.com.fiap.petapi.models.Arquivo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class ArquivoRepository extends AbstractRepository<Arquivo> {

    @Autowired
    public ArquivoRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Arquivo cadastrar(Arquivo arquivo) {
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tamanho", arquivo.getTamanho());
        params.addValue("nome", arquivo.getNomeArquivo());
        params.addValue("tipo", arquivo.getTipo().name());
        params.addValue("subpasta", arquivo.getSubpasta());

        String sql = "INSERT INTO " + Arquivo.TABLE_NAME + " (nr_tamanho, nm_original, ds_tipo, ds_subpasta) " +
                "VALUES (:tamanho, :nome, :tipo, :subpasta)";

        try {
            namedParameterJdbcTemplate.update(sql, params, holder, new String[] {"cd_arquivo"});
            arquivo.setId(Objects.requireNonNull(holder.getKey()).longValue());
        } catch (DataAccessException e) {
            log.error("Erro ao salvar o arquivo no banco de dados.", e);
        }
        return arquivo;
    }

    @Override
    public Optional<Arquivo> buscarPorId(long id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);

        String sql = "SELECT cd_arquivo, nr_tamanho, nm_original, ds_tipo, ds_subpasta, dt_criacao" +
                " FROM " + br.com.fiap.petapi.models.Arquivo.TABLE_NAME + " WHERE cd_arquivo = :id";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    param,
                    (rs, rowNum) -> Optional.of(new Arquivo.Builder()
                            .setId(rs.getLong("id"))
                            .setTamanho(rs.getDouble("nr_tamanho"))
                            .setNomeArquivo(rs.getString("nm_original"))
                            .setTipo(TipoArquivo.valueOf(rs.getString("ds_tipo")))
                            .setSubpasta(rs.getString("ds_subpasta"))
                            .setCadastradoEm(rs.getObject("dt_criacao", LocalDateTime.class))
                            .build())
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Arquivo> listarTodos() {
        String sql = "SELECT cd_arquivo, nr_tamanho, nm_original, ds_tipo, ds_subpasta, dt_criacao" +
                " FROM " + br.com.fiap.petapi.models.Arquivo.TABLE_NAME;

        return this.jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Arquivo.Builder()
                        .setId(rs.getLong("id"))
                        .setTamanho(rs.getDouble("nr_tamanho"))
                        .setTipo(TipoArquivo.valueOf(rs.getString("ds_tipo")))
                        .setSubpasta(rs.getString("ds_subpasta"))
                        .setCadastradoEm(rs.getObject("dt_criacao", LocalDateTime.class))
                        .build()
        );
    }

    @Override
    public int remover(long id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        String sql = "DELETE FROM " + Arquivo.TABLE_NAME + " WHERE id = :id";
        return namedParameterJdbcTemplate.update(sql, param);
    }
}
