package com.skn.admin.config.security;

import com.skn.admin.environment.dto.Admin;
import com.skn.admin.config.security.password.KisaSha256PasswordEncoder;
import com.skn.admin.environment.dto.AdminLoginLog;
import com.skn.admin.environment.service.AdminLoginLogService;
import com.skn.admin.util.NTUtil;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: Spring Security 환경설정
 */

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment env;

    @Autowired
    private AdminLoginLogService adminLoginLogService;

    @Bean
    public KisaSha256PasswordEncoder passwordEncoder() {
        return new KisaSha256PasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/assets/**")));
    }


    protected void authorizationUrl(HttpSecurity http) throws Exception {

        if (Objects.equals(env.getProperty("spring.profiles.active"), "local")) {
            http.authorizeHttpRequests(request -> request
                    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                    .anyRequest().permitAll()
            );
        } else {
            http.authorizeHttpRequests(request -> request
                    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/account/password/**", "POST")).permitAll()
                    .anyRequest().authenticated()
            );
        }
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String clientIp = InetAddress.getLocalHost().getHostAddress().toString();
        http.csrf().disable().cors().disable();

        authorizationUrl(http);
        http
                // xss (테스트 필요)
//            .headers(header -> header
//                    .xssProtection()
//                    .and()
//                    .contentSecurityPolicy("script-src 'self'")
//            )

                // Form login
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/api/v1/login")
                        .usernameParameter("adminId")
                        .passwordParameter("adminPw")
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailHandler())
                        .permitAll()
                )
                // logout
                .logout(out -> out
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .addLogoutHandler(new LogoutHandler() {
                            @Override
                            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                                Admin adminAccount = (Admin) authentication.getPrincipal();
                                // 로그인 실패 로그 저장
                                adminLoginLogService.insertLoginLog(AdminLoginLog.builder()
                                                .adminIndex(adminAccount.getAdminIndex())
                                                .adminId(adminAccount.getAdminId())
                                                .loginDate(adminAccount.getLastLoginDate())
                                                .ip(NTUtil.getClientIp(request))
                                                .result("로그아웃")
                                        .build()
                                );
                            }
                        })
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public CompositeSessionAuthenticationStrategy concurrentSession() {
        ConcurrentSessionControlAuthenticationStrategy concurrentAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        List<SessionAuthenticationStrategy> delegateStrategies = new ArrayList<SessionAuthenticationStrategy>();
        delegateStrategies.add(concurrentAuthenticationStrategy);
        delegateStrategies.add(new SessionFixationProtectionStrategy());
        delegateStrategies.add(new RegisterSessionAuthenticationStrategy(sessionRegistry()));

        return new CompositeSessionAuthenticationStrategy(delegateStrategies);
    }

    @Bean
    SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy("/login");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        AuthenticationSuccessHandler handler = new AuthenticationSuccessHandler();
        handler.setAlwaysUseDefaultTargetUrl(false);
        handler.setUseReferer(false);
        handler.setTargetUrlParameter("returnUrl");
        return handler;
    }

    @Bean
    public AuthenticationFailHandler authenticationFailHandler() {
        AuthenticationFailHandler handler = new AuthenticationFailHandler();
        return handler;
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
}

