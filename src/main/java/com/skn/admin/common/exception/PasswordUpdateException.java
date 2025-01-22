package com.skn.admin.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: Spring Security 인증단계에서 사용할 커스텀 Exception (로그인 시 비밀번호 변경필요한 경우)
 */

public class PasswordUpdateException extends AuthenticationException {
    public PasswordUpdateException(String message) {
        super(message);
    }
}
