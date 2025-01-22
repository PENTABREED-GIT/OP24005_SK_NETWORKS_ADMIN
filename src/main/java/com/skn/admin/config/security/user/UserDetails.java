package com.skn.admin.config.security.user;

import com.skn.admin.environment.dto.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private Admin admin;
    private List<?> authorities;

    private boolean isLogin;

    private Integer adminIndex;
    private String adminId;
    private String adminPw;

    public UserDetails(Admin admin) {
        setAdmin(admin);
        this.admin = admin;
    }

    public void setAdmin(Admin admin) {
        if (admin == null) {
            this.isLogin = false;
        } else {
            this.isLogin = true;
            this.adminId = admin.getAdminId();
            this.adminPw = admin.getAdminPw();
            this.adminIndex = admin.getAdminIndex();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if (isLogin()) {
            authorities.add(new SimpleGrantedAuthority("IS_AUTHENTICATED_FULLY"));
        } else {
            authorities.add(new SimpleGrantedAuthority("IS_AUTHENTICATED_ANONYMOUSLY"));
        }
        return authorities;
    }

    public boolean isLogin() {
        return isLogin;
    }

    @Override
    public String getPassword() {
        return adminPw;
    }

    @Override
    public String getUsername() {
        return adminId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Admin getAdmin() {
        return admin;
    }
}
