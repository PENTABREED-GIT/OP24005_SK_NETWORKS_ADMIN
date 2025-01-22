package com.skn.admin.config.security;

import com.skn.admin.config.security.user.AdminAdaptor;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: Spring Security 에서 사용하는 Custom UserDetailsService 구현체
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {
    private final AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {
        Optional<Admin> _manager = Optional.ofNullable(adminService.selectAdminById(adminId));
        Admin admin = _manager.orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new AdminAdaptor(admin);
    }
}
