package com.skn.admin.config.security;

import com.skn.admin.config.security.password.KisaSha256PasswordEncoder;
import com.skn.admin.config.security.user.AdminAdaptor;
import com.skn.admin.environment.dto.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: Spring Security 인증을 실행하는 Provider 클래스
 */

@Slf4j
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    @Autowired
    private AuthenticationUserDetailService userDetailService;
    @Autowired
    private KisaSha256PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("AuthenticationProvider!!");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String id = token.getName();
        String pwd = (String) token.getCredentials();
        String encPwd = passwordEncoder.encode(pwd);

        AdminAdaptor adminAdaptor = (AdminAdaptor) userDetailService.loadUserByUsername(id);
        Admin admin = adminAdaptor.getAdmin();

        log.debug("inp password: {}", pwd);
        log.debug("inp enc password: {}", encPwd);
        log.debug("save password: {}", adminAdaptor.getPassword());


        if (admin.getLoginFailCount() >= 5) { // 잠긴 계정
            throw new LockedException(admin.getAdminId() + ": Account Locked");
        }

        if (admin.getIsUse().equals("N")) { // 사용중지된 계정
            throw new LockedException(admin.getAdminId() + ": Account Disabled");
        }

        if (!passwordEncoder.matches(encPwd, adminAdaptor.getPassword())) { // 패스워드가 틀린경우
            throw new BadCredentialsException(admin.getAdminId() + ": Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(admin, pwd, adminAdaptor.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
