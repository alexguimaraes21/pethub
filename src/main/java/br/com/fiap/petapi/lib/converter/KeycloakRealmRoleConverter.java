package br.com.fiap.petapi.lib.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        try {
            final Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
            return ((List<String>) realmAccess.get("roles")).stream()
                    .map(roleName -> "ROLE_" + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            log.error("Esse usuário não possui nenhuma Role Atribuída"); // Desativado temporariamente
            return null;
        } catch (Exception e) {
            log.error("Erro desconhecido ao converter a Role do Realm");
            return null;
        }
    }
}
