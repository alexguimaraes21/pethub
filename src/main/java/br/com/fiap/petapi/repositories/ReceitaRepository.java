package br.com.fiap.petapi.repositories;

import br.com.fiap.petapi.models.Receita;
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
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class ReceitaRepository {

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public ReceitaRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                    JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Receita cadastrar(Receita receita) {
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nmRemedio", receita.getMedicamento());
        params.addValue("dsDosagem", receita.getDosagem());
        params.addValue("dsPeriodicidade", receita.getPeriodicidade());
        params.addValue("nrCrmv", receita.getCrmvVeterinario());
        params.addValue("nmVeterinario", receita.getNomeVeterinario());
        params.addValue("historicoSaudeId",receita.getHistoricoSaude().getId());

        String sql = "INSERT INTO " + Receita.TABLE_NAME + "(nm_remedio, ds_dosagem, ds_periodicidade, nr_crmv, " +
                "nm_veterinario, cd_historico_saude) VALUES (:nmRemedio, :dsDosagem, :dsPeriodicidade, :nrCrmv, " +
                ":nmVeterinario, :historicoSaudeId)";

        try {
            namedParameterJdbcTemplate.update(sql, params, holder, new String[] {"cd_receita"});
            receita.setId(Objects.requireNonNull(holder.getKey()).longValue());
        } catch (DataAccessException e) {
            log.error("Erro ao salvar no banco de dados.", e);
        }
        return receita;
    }

    public Optional<Receita> buscarPorHistoricoSaudeId(long historicoSaudeId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("historicoSaudeId", historicoSaudeId);

        String sql = "SELECT nm_remedio, ds_dosagem, ds_periodicidade, nr_crmv, nm_veterinario " +
                "FROM " + Receita.TABLE_NAME + " WHERE cd_historico_saude = :historicoSaudeId";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    param,
                    (rs, rowNum) -> Optional.of(new Receita.Builder()
                            .setMedicamento(rs.getString("nm_remedio"))
                            .setDosagem(rs.getString("ds_dosagem"))
                            .setCrmvVeterinario(rs.getString("nr_crmv"))
                            .setNomeVeterinario(rs.getString("nm_veterinario"))
                            .setPeriodicidade(rs.getString("ds_periodicidade"))
                            .build()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int remover(long historicoId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("historicoId", historicoId);
        String sql = "DELETE FROM " + Receita.TABLE_NAME + " WHERE cd_historico_saude = :historicoId";
        return namedParameterJdbcTemplate.update(sql, param);
    }
}
