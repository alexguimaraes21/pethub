package br.com.fiap.petapi.lib.crypt;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncrypt {

    private PasswordEncrypt() {}

    public static String encode(String password) {
        return getDefaultEncoder().encode(password);
    }

    public static PasswordEncoder getDefaultEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 65536, 4);
    }

    public static Boolean validatePassword(String password, String encodedPassword) {
        PasswordEncoder encoder = getDefaultEncoder();
        return encoder.matches(password, encodedPassword);
    }
}
