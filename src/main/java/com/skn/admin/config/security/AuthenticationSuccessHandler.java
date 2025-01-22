package com.skn.admin.config.security;

import com.skn.admin.account.service.AccountService;
import com.skn.admin.common.exception.PasswordUpdateException;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.dto.AdminLoginLog;
import com.skn.admin.environment.service.AdminLoginLogService;
import com.skn.admin.environment.service.AdminService;
import com.skn.admin.util.NTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: Spring Security 인증 성공시 실행되는 Handler 클래스 (Provider -> SuccessHandler)
 */

@Slf4j
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements DefaultAuthenticationHandler {

    @Autowired
    private AccountService accoutService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminLoginLogService adminLoginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        result.put("resultCode", "SUCCESS");

        Admin admin = (Admin) authentication.getPrincipal();

        String ip = NTUtil.getClientIp(request);

        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("ip", ip);
        loginMap.put("adminId", admin.getAdminId());
        //로그인 아이피, 최종로그인 일자 갱신, 로그인 실패 횟수 카운트 초기화
        accoutService.updateLogInInfo(loginMap);
        
        // 비밀번호 변경 확인
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String nowDate = String.valueOf(now);
        String lastLogin = admin.getLastLoginDate();
        String lastPwMod = admin.getPwModDate();
        Date currentDate = null;
        Date lastLoginDate = null;
        Date lastPwModDate = null;
        try {
            currentDate = format.parse(nowDate);
            lastLoginDate = format.parse(lastLogin);
            lastPwModDate = format.parse(lastPwMod);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        result.put("lastLoginDate", lastLogin);
        result.put("lastIp", ip);

        HttpSession session = request.getSession();
        session.setAttribute("ADMIN", admin);

        String sessionToken = NTUtil.getRandomGUID();
        Map<String, String> aMap = new HashMap<String, String>();
        aMap.put("adminIndex", String.valueOf(admin.getAdminIndex()));
        aMap.put("sessionToken", sessionToken);
        adminService.insertAdminSession(aMap);

        //String pwPeriod = (session.getAttribute("pwPeriod")).toString();
        // 관리자는 90일 기준으로 비밀번호 변경 확인
        if (NTUtil.getDateDiff("d", currentDate, lastPwModDate) > 90) {
            session.setAttribute("adminSessionToken", sessionToken);
            throw new PasswordUpdateException(admin.getAdminId() + ": Update password");
        }
        session.setAttribute("adminSessionToken", sessionToken);
        // 로그인 성공 로그 저장

        adminLoginLogService.insertLoginLog(AdminLoginLog.builder()
                .adminId(admin.getAdminId())
                .loginDate(admin.getLastLoginDate())
                .ip(ip)
                .result("로그인")
                .adminIndex(admin.getAdminIndex())
                .build()
        );

        boolean isWrite = isJson(request);
        if (isWrite) {
            writeResponse(request, response, null, "login success", result);
            clearAuthenticationAttributes(request);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    public static boolean isJson(HttpServletRequest request) {
        if (request == null || request.getHeader("Accept") == null) {
            return false;
        }

        String accept = request.getHeader("Accept").toLowerCase(Locale.ENGLISH);
        return accept.contains(MediaType.APPLICATION_JSON_VALUE);
    }
}
