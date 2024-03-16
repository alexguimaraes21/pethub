package br.com.fiap.petapi.responsemodel;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponseModel {
    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private List<?> errors;
    private String path;
}
