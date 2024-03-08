package br.com.fiap.petapi.services;

import br.com.fiap.petapi.exceptions.StorageException;
import br.com.fiap.petapi.models.Arquivo;
import br.com.fiap.petapi.repositories.ArquivoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
public class StorageService {

    private String basePath;
    ArquivoRepository arquivoRepository;

    @Autowired
    public StorageService(Environment env,
                          ArquivoRepository arquivoRepository) {
        this.basePath = env.getProperty("app.upload.base-path");
        this.arquivoRepository = arquivoRepository;
    }

    public void store(MultipartFile file, long fileId) {
        Path dstFolder = this.createDstFolder();
        try {
            if(file.isEmpty()) {
                throw new StorageException("Falha ao armazenar o arquivo. Arquivo vazio.");
            }
            Path destinationFile = dstFolder.resolve(Paths.get("file_" + fileId))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(dstFolder.toAbsolutePath())) {
                throw new StorageException("Não é possível armazenar os arquivos em diretórios diferentes do pré-definido.");
            }
            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            log.error("Erro ao armazenar o arquivo no sistema de arquivos.", e);
            throw new StorageException("Erro ao armazenar o arquivo no sistema de arquivos", e);
        }
    }

    public Path load(long fileId) {
        Optional<Arquivo> arquivo = arquivoRepository.buscarPorId(fileId);
        Path dstFolder = null;
        if (arquivo.isPresent()) {
            dstFolder = Paths.get(this.basePath + "/" + arquivo.get().getSubpasta());
            return dstFolder.resolve("file_" + fileId);
        }
        return null;
    }

    private Path createDstFolder() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        String uploadPathYearSufix = localDate.format(year);
        DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
        String uploadPathMonthSufix = localDate.format(month);
        File dstYearAndMonthFolder = new File(basePath + "/" + year + "/" + month);
        Path dstFolder = Paths.get(basePath + "/" + uploadPathYearSufix + "/" + uploadPathMonthSufix);
        if (!dstYearAndMonthFolder.exists()) {
            try {
                Files.createDirectories(dstFolder);
            } catch (IOException e) {
                log.error("Erro ao criar diretório para armazenar os arquivos.", e);
            }
        }
        log.warn(basePath + "/" + uploadPathYearSufix + "/" + uploadPathMonthSufix);
        return dstFolder;
    }
}
