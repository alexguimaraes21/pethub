package br.com.fiap.petapi.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Server extends io.swagger.v3.oas.models.servers.Server {

    private String url;
    private String description;
}
