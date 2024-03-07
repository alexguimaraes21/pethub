package br.com.fiap.petapi.services;

import br.com.fiap.petapi.exceptions.StorageException;
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

@Slf4j
@Service
public class StorageService {

    private String basePath;

    private final Path dstFolder;

    @Autowired
    public StorageService(Environment env) {
        this.basePath = env.getProperty("app.upload.base-path");
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
        log.warn(this.basePath);
        log.warn(basePath + "/" + uploadPathYearSufix + "/" + uploadPathMonthSufix);
        this.dstFolder = dstFolder;
    }

    public void store(MultipartFile file, long fileId) {
        try {
            if(file.isEmpty()) {
                throw new StorageException("Falha ao armazenar o arquivo. Arquivo vazio.");
            }
            Path destinationFile = this.dstFolder.resolve(Paths.get("file_" + fileId))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.dstFolder.toAbsolutePath())) {
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
        log.info(this.dstFolder.resolve("file_" + fileId).toString());
        return this.dstFolder.resolve("file_" + fileId);
    }
}
