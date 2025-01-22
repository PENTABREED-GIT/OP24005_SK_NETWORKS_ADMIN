package com.skn.admin.config.security;

import com.skn.admin.account.mapper.AccountMapper;
import com.skn.admin.common.exception.PasswordUpdateException;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.dto.AdminLoginLog;
import com.skn.admin.environment.mapper.AdminMapper;
import com.skn.admin.environment.service.AdminLoginLogService;
import com.skn.admin.environment.service.AdminService;
import com.skn.admin.util.NTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: Spring Security 인증 실패시 실행되는 Handler 클래스 (Provider -> FailHandler)
 */

@Slf4j
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler implements DefaultAuthenticationHandler {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminLoginLogService adminLoginLogService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (NTUtil.isEmpty(clientIp)) {
            clientIp = request.getRemoteAddr();
        } else {
            // X-Forwarded-For 헤더에 여러 IP가 있을 경우 첫 번째 IP를 사용
            clientIp = (clientIp + ",").split(",")[0].trim();
        }
        String failMsg = "로그인 실패";

        Map<String, Object> result = new HashMap<>();
        result.put("resultCode", "Fail");

        String id = request.getParameter("adminId");
        Admin admin = adminService.selectAdminById(id);
        int updateFailCount = admin.getLoginFailCount() + 1;
        String adminIsUse = Optional.ofNullable(admin.getIsUse()).orElse("N");

        Map paramMap = new HashMap();
        paramMap.put("adminId", admin.getAdminId());
        paramMap.put("adminIndex", admin.getAdminIndex());

        if (exception instanceof LockedException) { // 계정이 잠긴 경우
            if (adminIsUse.equals("N")) {
                result.put("result", "LOCKED");
                result.put("message", "사용하지 못하는 계정입니다. 관리자에게 문의하세요.");

                failMsg = "잠긴 계정";
            } else {
                result.put("result", "LOCKED");
                result.put("message", "5회이상 잘못된 비밀번호를 입력하여 해당계정이 차단됩니다. 관리자에게 문의하세요.");

                failMsg = "비밀번호 5회 오류";
            }
        } else if (exception instanceof BadCredentialsException) { // 아이디, 비밀번호 일치하지 않는경우
            result.put("result", "PWD NOT MATCHED");
            result.put("message", "아이디/패스워드를 확인해주세요.");

            if (updateFailCount == 5) {
                paramMap.put("useYN", "N");
                accountMapper.updateLoginFailCount(paramMap);
                accountMapper.updateUseYN(paramMap);

                result.put("result", "LOCKED");
                result.put("message", "5회이상 잘못된 비밀번호를 입력하여 해당계정이 차단됩니다. 관리자에게 문의하세요.");

                failMsg = "비밀번호 5회 오류";
            } else if (updateFailCount > 5) {
                //계정 상태값 미사용으로 갱신
                accountMapper.updateLoginFailCount(paramMap);

                result.put("result", "LOCKED");
                result.put("message", "5회이상 잘못된 비밀번호를 입력하여 해당계정이 차단됩니다. 관리자에게 문의하세요.");

                failMsg = "비밀번호 5회 오류";
            } else {
                //로그인 실패횟수 증가
                accountMapper.updateLoginFailCount(paramMap);

                failMsg = "비밀번호 오류";
            }
        } else if (exception instanceof UsernameNotFoundException) { // 해당 계정이 없는 경우
            result.put("result", "NOT FOUND");
            result.put("message", "잘못된 계정 정보입니다.");
        } else if (exception instanceof PasswordUpdateException) { // 비밀번호를 변경해야 하는경우
            result.put("result", "MOD PASSWORD");
            result.put("uid", admin.getUid());
            result.put("message", "비밀번호를 변경해주세요.");
        } else {
            result.put("result", "FAIL");
            result.put("message", "로그인이 실패하였습니다.");

            //로그인 실패횟수 증가
            accountMapper.updateLoginFailCount(paramMap);
        }

        //로그인 실패한 경우, 기존 로그인세션토큰 삭제
        adminMapper.deleteAdminSession(paramMap);

        // 로그인 실패 로그 저장
        adminLoginLogService.insertLoginLog(AdminLoginLog.builder()
                .adminIndex(admin.getAdminIndex())
                .adminId(admin.getAdminId())
                .loginDate(admin.getLastLoginDate())
                .ip(clientIp)
                .result(failMsg)
                .build());

        boolean isWrite = isJson(request);
        if (isWrite) {
            writeResponse(request, response, null, "login fail", result);
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
        request.getSession().invalidate();
    }

    public static boolean isJson(HttpServletRequest request) {
        if (request == null || request.getHeader("Accept") == null) {
            return false;
        }

        String accept = request.getHeader("Accept").toLowerCase(Locale.ENGLISH);
        return accept.contains(MediaType.APPLICATION_JSON_VALUE);
    }
}
