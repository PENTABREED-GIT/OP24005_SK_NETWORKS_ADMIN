package com.skn.admin.config.security.user;

import com.skn.admin.environment.dto.Admin;
import lombok.Getter;

@Getter
public class AdminAdaptor extends UserDetails {
    private final Admin admin;

    public AdminAdaptor(Admin admin) {
        super(admin);
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }
}
