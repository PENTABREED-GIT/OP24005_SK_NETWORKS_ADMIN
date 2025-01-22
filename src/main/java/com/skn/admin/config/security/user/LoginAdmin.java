package com.skn.admin.config.security.user;

import com.skn.admin.environment.dto.Admin;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: 로그인 객체 활용 클래스
 */
public class LoginAdmin {

    /**
     * Security Session에서 Manager 객체를 가져옴
     * @return
     */
    public static Admin getCurrentAdmin(HttpServletRequest request) {
        Admin admin = null;
        try {
            //admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            admin = (Admin) request.getSession().getAttribute("ADMIN");
        } catch (Exception e) {
            admin = null;
        }
        return admin;
    }
}
