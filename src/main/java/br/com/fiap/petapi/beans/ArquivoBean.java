package br.com.fiap.petapi.beans;

import br.com.fiap.petapi.enums.TipoArquivo;
import br.com.fiap.petapi.models.Arquivo;
import br.com.fiap.petapi.repositories.ArquivoRepository;
import br.com.fiap.petapi.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class ArquivoBean {

    ArquivoRepository arquivoRepository;
    StorageService storageService;

    @Autowired
    public ArquivoBean(ArquivoRepository arquivoRepository,
                       StorageService storageService) {
        this.arquivoRepository = arquivoRepository;
        this.storageService = storageService;
    }

    public Arquivo cadastraArquivo(MultipartFile file) throws IOException {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter subfolder = DateTimeFormatter.ofPattern("yyyy/MM");
        Arquivo novoArquivo = new Arquivo();
        novoArquivo.setNomeArquivo(file.getOriginalFilename());
        novoArquivo.setTamanho(file.getSize());
        novoArquivo.setTipo(TipoArquivo.valueOf(FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase()));
        novoArquivo.setSubpasta(localDate.format(subfolder));
        novoArquivo.setCadastradoEm(LocalDateTime.now());
        Arquivo arquivoSalvo = arquivoRepository.cadastrar(novoArquivo);
        log.warn(arquivoSalvo.toString());
        storageService.store(file, arquivoSalvo.getId());

        return novoArquivo;
    }
}
