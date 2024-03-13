package br.com.fiap.petapi.repositories;

import br.com.fiap.petapi.models.Animal;
import br.com.fiap.petapi.models.HistoricoSaude;
import br.com.fiap.petapi.models.Usuario;
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
public class HistoricoSaudeRepository{

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoricoSaudeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                    JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public HistoricoSaude cadastrar(HistoricoSaude historicoSaude) {
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cadastradoEm", historicoSaude.getCadastradoEm());
        params.addValue("animalId", historicoSaude.getAnimal().getId());

        String sql = "INSERT INTO " + HistoricoSaude.TABLE_NAME + " (dt_cadastro, cd_animal) " +
                "VALUES (:cadastradoEm, :animalId)";

        try {
            namedParameterJdbcTemplate.update(sql, params, holder, new String[] {"cd_historico_saude"});
            historicoSaude.setId(Objects.requireNonNull(holder.getKey()).longValue());
        } catch (DataAccessException e) {
            log.error("Erro ao salvar no banco de dados.", e);
        }
        return historicoSaude;
    }

    public Optional<HistoricoSaude> buscarPorId(long historicoId, long animalId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("historicoId", historicoId);
        params.addValue("animalId", animalId);

        String sql = "SELECT cd_historico_saude, dt_cadastro FROM " + HistoricoSaude.TABLE_NAME +
                " WHERE cd_historico_saude = :historicoId AND cd_animal = :animalId";
        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    params,
                    (rs, rowNum) -> Optional.of(new HistoricoSaude.Builder()
                            .setId(rs.getLong("cd_historico_saude"))
                            .setCadastradoEm(rs.getObject("dt_cadastro", LocalDateTime.class))
                            .build())
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<HistoricoSaude> listarTodos(long animalId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("animalId", animalId);

        String sql = "SELECT cd_historico_saude, dt_cadastro FROM " + HistoricoSaude.TABLE_NAME +
                " WHERE cd_animal = :animalId";
        return this.namedParameterJdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> new HistoricoSaude.Builder()
                        .setId(rs.getLong("cd_historico_saude"))
                        .setCadastradoEm(rs.getObject("dt_cadastro", LocalDateTime.class))
                        .build());
    }

    public int remover(long historicoId, long animalId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("historicoId", historicoId);
        param.addValue("animalId", animalId);
        String sql = "DELETE FROM " + HistoricoSaude.TABLE_NAME + " WHERE cd_animal = :animalId AND cd_historico_saude = :historicoId";
        return namedParameterJdbcTemplate.update(sql, param);
    }
}
