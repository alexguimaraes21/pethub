package br.com.fiap.petapi.repositories;

import br.com.fiap.petapi.models.HistoricoSaude;
import br.com.fiap.petapi.models.Vacina;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class VacinaRepository {

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public VacinaRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                    JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Vacina cadastrar(Vacina vacina) {
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dtVacina", vacina.getData());
        params.addValue("dsVacina", vacina.getDescricao());
        params.addValue("historicoSaudeId", vacina.getHistoricoSaude().getId());

        String sql = "INSERT INTO " + Vacina.TABLE_NAME + " (dt_vacina, ds_vacina, cd_historico_saude) " +
                "VALUES (:dtVacina, :dsVacina, :historicoSaudeId)";

        try {
            namedParameterJdbcTemplate.update(sql, params, holder, new String[] {"cd_vacina"});
            vacina.setId(Objects.requireNonNull(holder.getKey()).longValue());
        } catch (DataAccessException e) {
            log.error("Erro ao salvar no banco de dados.", e);
        }
        return vacina;
    }

    public Optional<Vacina> buscarPorHistoricoSaudeId(long historicoSaudeId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("historicoSaudeId", historicoSaudeId);

        String sql = "SELECT dt_vacina, ds_vacina FROM " + Vacina.TABLE_NAME + " " +
                "WHERE cd_historico_saude = :historicoSaudeId";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    param,
                    (rs, rowNum) -> Optional.of(new Vacina.Builder()
                            .setData(rs.getObject("dt_vacina", LocalDate.class))
                            .setDescricao(rs.getString("ds_vacina"))
                            .build()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int remover(long historicoId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("historicoId", historicoId);
        String sql = "DELETE FROM " + Vacina.TABLE_NAME + " WHERE cd_historico_saude = :historicoId";
        return namedParameterJdbcTemplate.update(sql, param);
    }
}
