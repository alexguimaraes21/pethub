package br.com.fiap.petapi.repositories;

import br.com.fiap.petapi.models.Animal;
import br.com.fiap.petapi.models.Arquivo;
import br.com.fiap.petapi.models.Usuario;
import br.com.fiap.petapi.services.UsuarioService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class AnimalRepository extends AbstractRepository<Animal> {

    UsuarioService usuarioService;

    @Autowired
    public AnimalRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                            JdbcTemplate jdbcTemplate,
                            UsuarioService usuarioService) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.usuarioService = usuarioService;
    }

    @Override
    public Animal cadastrar(Animal animal) {
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nome", animal.getNome());
        params.addValue("nomeCientifico", animal.getNomeCientifico());
        params.addValue("nomeEspecie", animal.getNomeEspecie());
        params.addValue("cor", animal.getCor());
        params.addValue("codigoChip", animal.getCodigoChip());
        params.addValue("codigoTatuagem", animal.getCodigoTatuagem());
        params.addValue("dataNascimento", animal.getDataNascimento());
        params.addValue("tamanhoPorte", animal.getTamanhoPorte());
        params.addValue("peso", animal.getPeso());
        params.addValue("temperamento", animal.getTemperamento());
        params.addValue("raca", animal.getRaca());
        params.addValue("pessoaId", animal.getPessoa().getId());

        String sql = "";
        if (animal.getFoto() != null) {
            params.addValue("foto", animal.getFoto().getId());
            sql = "INSERT INTO " + Animal.TABLE_NAME + " (nm_animal, nm_cientifico, nm_especie, ds_cor, ds_chip, ds_tatuagem, " +
                    "dt_nascimento, ds_tamanho, nr_peso, ds_temperamento, cd_pessoa, ds_raca, cd_arquivo) " +
                    "VALUES (:nome, :nomeCientifico, :nomeEspecie, :cor, :codigoChip, :codigoTatuagem, :dataNascimento, " +
                    ":tamanhoPorte, :peso, :temperamento, :pessoaId, :raca, :foto)";
        } else {
            sql = "INSERT INTO " + Animal.TABLE_NAME + " (nm_animal, nm_cientifico, nm_especie, ds_cor, ds_chip, ds_tatuagem, " +
                    "dt_nascimento, ds_tamanho, nr_peso, ds_temperamento, cd_pessoa, ds_raca) " +
                    "VALUES (:nome, :nomeCientifico, :nomeEspecie, :cor, :codigoChip, :codigoTatuagem, :dataNascimento, " +
                    ":tamanhoPorte, :peso, :temperamento, :pessoaId, :raca)";
        }

        try {
            namedParameterJdbcTemplate.update(sql, params, holder, new String[] {"cd_animal"});
            animal.setId(Objects.requireNonNull(holder.getKey()).longValue());
        } catch (DataAccessException e) {
            log.error("Erro ao salvar o arquivo no banco de dados.", e);
        }
        return animal;
    }

    public Animal atualizar(Animal animal) {
        KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nome", animal.getNome());
        params.addValue("nomeCientifico", animal.getNomeCientifico());
        params.addValue("nomeEspecie", animal.getNomeEspecie());
        params.addValue("cor", animal.getCor());
        params.addValue("codigoChip", animal.getCodigoChip());
        params.addValue("codigoTatuagem", animal.getCodigoTatuagem());
        params.addValue("dataNascimento", animal.getDataNascimento());
        params.addValue("tamanhoPorte", animal.getTamanhoPorte());
        params.addValue("peso", animal.getPeso());
        params.addValue("temperamento", animal.getTemperamento());
        params.addValue("raca", animal.getRaca());
        params.addValue("pessoaId", animal.getPessoa().getId());
        params.addValue("animalId", animal.getId());

        String sql = "";
        if (animal.getFoto() != null) {
            params.addValue("foto", animal.getFoto().getId());
            sql = "UPDATE " + Animal.TABLE_NAME + " SET nm_animal = :nome, nm_cientifico = :nomeCientifico, " +
                    "nm_especie = :nomeEspecie, ds_cor = :cor, ds_chip = :codigoChip, ds_tatuagem = :codigoTatuagem, " +
                    "dt_nascimento = :dataNascimento, ds_tamanho = :tamanhoPorte, nr_peso = :peso, " +
                    "ds_temperamento = :temperamento, ds_raca = :raca WHERE cd_pessoa = :pessoaId AND cd_animal = :animalId";
        } else {
            sql = "INSERT INTO " + Animal.TABLE_NAME + " (nm_animal, nm_cientifico, nm_especie, ds_cor, ds_chip, ds_tatuagem, " +
                    "dt_nascimento, ds_tamanho, nr_peso, ds_temperamento, cd_pessoa, ds_raca) " +
                    "VALUES (:nome, :nomeCientifico, :nomeEspecie, :cor, :codigoChip, :codigoTatuagem, :dataNascimento, " +
                    ":tamanhoPorte, :peso, :temperamento, :pessoaId, :raca)";
        }

        try {
            namedParameterJdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            log.error("Erro ao salvar o arquivo no banco de dados.", e);
        }
        return animal;
    }

    @Override
    public Optional<Animal> buscarPorId(long id) {
        Optional<Usuario> usuario = usuarioService.retornaUsuarioAutenticado();
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("pessoaId", usuario.get().getPessoa().getId());
        param.addValue("id", id);

        String sql = "SELECT cd_animal, nm_animal, nm_cientifico, nm_especie, ds_cor, ds_chip, ds_tatuagem, " +
                " dt_nascimento, ds_tamanho, nr_peso, ds_temperamento, cd_pessoa, ds_raca, cd_arquivo" +
                " FROM " + Animal.TABLE_NAME + " WHERE cd_animal = :id AND cd_pessoa = :pessoaId";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    param,
                    (rs, rowNum) -> Optional.of(new Animal.Builder()
                            .setId(rs.getLong("cd_animal"))
                            .setNome(rs.getString("nm_animal"))
                            .setNomeCientifico(rs.getString("nm_cientifico"))
                            .setNomeEspecie(rs.getString("nm_especie"))
                            .setCor(rs.getString("ds_cor"))
                            .setCodigoChip(rs.getString("ds_chip"))
                            .setCodigoTatuagem(rs.getString("ds_tatuagem"))
                            .setDataNascimento(rs.getObject("dt_nascimento", LocalDate.class))
                            .setTamanhoPorte(rs.getString("ds_tamanho"))
                            .setPeso(rs.getDouble("nr_peso"))
                            .setTemperamento(rs.getString("ds_temperamento"))
                            .setFoto(new Arquivo.Builder()
                                    .setId(rs.getLong("cd_arquivo"))
                                    .build()
                            ).build())
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Animal> listarTodos() {
        Optional<Usuario> usuario = usuarioService.retornaUsuarioAutenticado();
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("pessoaId", usuario.get().getPessoa().getId());
        log.info("Pessoa: " + usuario.get().getPessoa().getId());

        String sql = "SELECT cd_animal, nm_animal, nm_cientifico, nm_especie, ds_cor, ds_chip, ds_tatuagem, " +
                " dt_nascimento, ds_tamanho, nr_peso, ds_temperamento, cd_pessoa, ds_raca, cd_arquivo" +
                " FROM " + Animal.TABLE_NAME + " WHERE cd_pessoa = :pessoaId";

        return this.namedParameterJdbcTemplate.query(
                sql,
                param,
                (rs, rowNum) -> new Animal.Builder()
                        .setId(rs.getLong("cd_animal"))
                        .setNome(rs.getString("nm_animal"))
                        .setNomeCientifico(rs.getString("nm_cientifico"))
                        .setNomeEspecie(rs.getString("nm_especie"))
                        .setCor(rs.getString("ds_cor"))
                        .setCodigoChip(rs.getString("ds_chip"))
                        .setCodigoTatuagem(rs.getString("ds_tatuagem"))
                        .setDataNascimento(rs.getObject("dt_nascimento", LocalDate.class))
                        .setTamanhoPorte(rs.getString("ds_tamanho"))
                        .setPeso(rs.getDouble("nr_peso"))
                        .setTemperamento(rs.getString("ds_temperamento"))
                        .setFoto(new Arquivo.Builder()
                                .setId(rs.getLong("cd_arquivo"))
                                .build()
                        ).build());
    }

    @Override
    public int remover(long id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        Optional<Usuario> usuario = usuarioService.retornaUsuarioAutenticado();
        param.addValue("id", id);
        param.addValue("pessoaId", usuario.get().getPessoa().getId());
        String sql = "DELETE FROM " + Animal.TABLE_NAME + " WHERE cd_animal = :id AND cd_pessoa = :pessoaId";
        return namedParameterJdbcTemplate.update(sql, param);
    }
}
