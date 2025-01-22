package com.skn.admin.config.security.password;

import com.skn.admin.util.KISA_SHA256;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by 박현진.
 * Date: 2023-02-20
 * Description: Spring Security 에서 사용할 KISA SHA 256 PasswordEncoder
 */
public class KisaSha256PasswordEncoder implements PasswordEncoder {
    public String encode(CharSequence rawPassword) {
        return KISA_SHA256.Encrpyt(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
