package com.skn.admin.common.interceptor;

import com.skn.admin.common.ApplicationProperty;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.dto.AdminActionLog;
import com.skn.admin.environment.dto.Setting;
import com.skn.admin.environment.service.AdminActionLogService;
import com.skn.admin.environment.service.AdminService;
import com.skn.admin.environment.service.SettingService;
import com.skn.admin.log.mapper.LogManagementMapper;
import com.skn.admin.site.dto.AdminMenu;
import com.skn.admin.site.dto.menu.request.MenuRequest;
import com.skn.admin.site.service.AdminMenuService;
import com.skn.admin.site.service.AdminPrivilegeService;
import com.skn.admin.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by bestcure.
 * Date: 2021-02-13
 * Sample 인터셉터
 */

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    @Resource
    private AdminMenuService adminMenuService;

    @Resource
    private AdminActionLogService adminActionLogService;
    @Resource
    private SettingService settingService;
    @Resource
    private AdminService adminService;

    @Resource
    private LogManagementMapper logManagementMapper;

    @Resource
    private AdminPrivilegeService adminPrivilegeService;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        log.info("====== AdminInterceptor Start ==============");
        request.setAttribute("handler", handler);
        String url = request.getRequestURI();
        String data = NTUtil.isNull(request.getQueryString(),"");
        String clientIp = request.getHeader("X-Forwarded-For");
        if (NTUtil.isEmpty(clientIp)) {
            clientIp = request.getRemoteAddr();
        } else {
            // X-Forwarded-For 헤더에 여러 IP가 있을 경우 첫 번째 IP를 사용
            clientIp = (clientIp + ",").split(",")[0].trim();
        }
        //셋팅정보 세션에 담기
        Setting setting = (Setting) request.getSession().getAttribute("setting");
        if (NTUtil.isEmpty(setting)) {
            setting = settingService.selectSetting();
            request.getSession().setAttribute("setting", setting);
        }

        // 관리자 세션 에 있는 시간 정보 확인해서 자동 로그아웃 설정
        Long lastAccessTime = (Long) request.getSession().getAttribute("LAST_ACCESS_TIME");
        if (lastAccessTime != null && setting.getIsUseLifetime().equals("Y")) {
            long currentTime = System.currentTimeMillis();
            long timeOut = Integer.parseInt(setting.getLifetime()) * 60 * 1000;
            if (currentTime - lastAccessTime > timeOut) {
                request.getSession().invalidate();
                response.sendRedirect("/login");
                return false;
            }
        }

        Admin admin = (Admin) request.getSession().getAttribute("ADMIN");
        String isUseIp = setting.getIsUseIp();

        // 등록된 IP 대역이 있는 경우 접속자 IP 검증
        // 등록된 IP가 아닌 경우 사용자 사이트로 리디렉션
        Boolean isAuthCheck = true;
        if  (isUseIp.equals("Y")) { // 디버깅을 위한 로컬 IP
            String ipList = (String) setting.getSiteIpAll();
            if (!ipList.contains(clientIp)) {
                response.sendRedirect(ApplicationProperty.USER_SITE_URL);
                return false;
            }
        }



        if (url.equals("/login")) return true;

        //api는 통과
        if (url.toLowerCase().contains("/api/v1/")) {
            return true;
        }

        // 로그인 세션이 없는 경우 로그인 페이지로 이동 || 사용하지 않는 계정 체크
        if (NTUtil.isEmpty(admin) || admin.getIsUse().equals("N")) {
            response.sendRedirect("/login");
            return false;
        }

        // 중복 세션 검사하여 로그인 페이지 이동
        Map<String, Object> sessionChkMap = new HashMap<>();
        sessionChkMap.put("adminIndex",NTUtil.isNull(admin.getAdminIndex(),""));
        String sessionToken = NTUtil.isNull(adminService.selectAdminSessionToken(sessionChkMap));
        String oldToken = NTUtil.isNull(request.getSession().getAttribute("adminSessionToken"), "");

        if(!oldToken.equals(sessionToken)) {
            request.getSession().invalidate();
            response.sendRedirect("/");
            return false;
        }

        Map<String, String> map = new HashMap();
        int depth = 1;
        String isDisplay = "Y";

        List menuList = null;
        List leftList = null;

        // 접속 로그 접근권한 체크
        boolean logAccessCheck = false;
        // 대시보드로 리디렉션
        if ("/".equals(url)) {
            url = "/Index";
        }
        // 대시보드 접근권한 확인 후 url재편
//        String returnUrl = adminPrivilegeService.checkDashboardToReturnPath(url, admin.getAdminGroupIndex());
//        // 그룹 없을시 로그아웃 처리
//        if("/logout".equals(returnUrl)) {
//            request.getSession().invalidate();
//            response.sendRedirect("/");
//            return false;
//        }
//        // 루트와도 다르고 기존 url과도 다르다면 리디렉션 건다.
//        if ("/".equals(url) && !"/Index".equals(returnUrl)) {
//            response.sendRedirect(returnUrl);
//            return false;
//        }
//        if ("/".equals(url)) {
//            url = returnUrl;
//        }

        // pathvariable 추가
        String[] urlCheck = url.split("/");
        if(urlCheck.length > 3) {
            url = "/" + urlCheck[1] + "/" + urlCheck[2] + "/" + urlCheck[3];
        }

        // 수퍼관리자 또는 최상위 관리자인 경우 권한 체크 안함.
        if ("S".equals(NTUtil.isNull(admin.getAdminType())) || "1".equals(NTUtil.isNull(admin.getAdminGroupIndex(),""))) {
            menuList = adminMenuService.selectTopMenuList(MenuRequest.TopMenu.of());
            leftList = adminMenuService.selectLeftMenuList(MenuRequest.LeftMenu.of(depth, url));
            logAccessCheck = true;
        } else {
            if (isAuthCheck) {
                Integer adminGroupIndex = admin.getAdminGroupIndex();
                menuList = adminMenuService.selectTopMenuList(MenuRequest.TopMenu.of(adminGroupIndex));
                leftList = adminMenuService.selectLeftMenuList(MenuRequest.LeftMenu.of(adminGroupIndex, depth, url));

                logAccessCheck = adminMenuService.isExistByAdminGroupIndexAndUrl(adminGroupIndex, "/log/login-log");
            }
        }

        if (isAuthCheck) {
            AdminMenu pageInfo = (AdminMenu) adminMenuService.selectMenu(MenuRequest.Menu.of(depth, url));
            request.setAttribute("PAGE_INFO", pageInfo);
        }


        request.setAttribute("ADMIN_NAME", admin.getAdminName());
        request.setAttribute("ADMIN_ID", admin.getAdminId());
        request.setAttribute("ADMIN_GROUP_INDEX", admin.getAdminGroupIndex());
        request.setAttribute("ADMIN_IP", clientIp);
        request.setAttribute("ADMIN_UID", admin.getUid());

        String chkUrl = url.split("/")[1];
        chkUrl = chkUrl.equals("environment") ? "setting" : chkUrl;
        request.setAttribute("MENU_LIST", menuList);
        request.setAttribute("LEFT_LIST", leftList);
        request.setAttribute("USER_URL", ApplicationProperty.USER_SITE_URL);
        request.setAttribute("url", "/" + chkUrl);

        request.setAttribute("uploadUrl", ApplicationProperty.USER_UPLOAD_URL);
        request.setAttribute("logAccessCheck", logAccessCheck);
        return true;
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView
    ) throws Exception {

        // 세션에 사용 시간 기록
        HttpSession session = request.getSession();
        session.setAttribute("LAST_ACCESS_TIME", System.currentTimeMillis());

        if (handler instanceof HandlerMethod) {

            log.info("====== AdminInterceptor Start ==============");

            Admin admin = (Admin) request.getSession().getAttribute("ADMIN");
            /**
             * login 됐을때만
             */
            if (!NTUtil.isEmpty(admin)) {
                String actionType = request.getMethod();
                String clientIp = NTUtil.getClientIp(request);
                String description = "";
                String queryString = request.getQueryString();
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                String url = request.getRequestURI();
                String koMenu = logManagementMapper.getKoMenuByUrl(url);
                if (("").equals(NTUtil.isNull(koMenu, "")) && !url.contains("/api/v1/")) {
                    String[] parts = url.split("/");
                    if (parts.length > 2)
                        koMenu = logManagementMapper.getKoMenuByUrl(url.split("/")[2]);
                } else if (url.contains("/api/v1/")) {
                    String[] parts = url.split("/");
                    // 부분 문자열이 5개 이상인 경우, 즉 /api/v1/ir/ir-data/delete/...가 있는 경우
                    if (parts.length > 4) {
                        // 0부터 4까지의 부분 문자열을 재조합하여 base path를 반환
                        koMenu = logManagementMapper.getKoMenuByUrl(String.join("/", parts[1], parts[2], parts[3], parts[4]));
                    } else {
                        // 그렇지 않은 경우, 전체 URL이 base path
                        koMenu = logManagementMapper.getKoMenuByUrl(String.join(url));
                    }
                    url += "/post";
                }

                if (url.contains("-list")) {
                    koMenu = koMenu + " > 목록";
                } else if (url.contains("-form") || url.contains("-mod")) {
                    koMenu = koMenu + " > 등록/수정 폼";
                } else if (url.contains("/update/")) {
                    koMenu = koMenu + " > 수정";
                } else if (url.contains("/delete/")) {
                    koMenu = koMenu + " > 삭제";
                } else if (url.contains("/post")) {
                    koMenu = koMenu + " > 등록";
                } else {
                    koMenu = koMenu + " > 상세";
                }

                Operation operation = handlerMethod.getMethodAnnotation(Operation.class);
                String downloadReason = "";
                String data = "";
                String detail = "";
                if (operation != null) {
                    // description 값 가져오기
                    description = operation.value();
                    if (actionType.equals("GET")) {
                        data = queryString;

                    } else if (actionType.equals("POST") && !(url.contains("/update/") || url.contains("/delete/"))) {
                        String jsonData = (String) request.getAttribute("requestBody");
                        data = jsonData;
                        if (operation.value().contains("엑셀 다운로드")) {
                            actionType = "DOWNLOAD";
                            String[] arr = data.split(",");
                            String temp = "";

                            for (String x : arr) {
                                if (!x.contains("downloadRequest")) {
                                    temp += x + ",";
                                } else {
                                    downloadReason = x.split(":")[1].replaceAll("\"", "");
                                    downloadReason = downloadReason.substring(0, downloadReason.length() - 1);
                                    data = temp.substring(0, temp.length() - 1) + "}";
                                    break;
                                }
                            }
                        }
                    } else if (actionType.equals("POST") && url.contains("/update/")) {
                        String jsonData = (String) request.getAttribute("requestBody");
                        data = "Parameter : " + queryString + "Json : " + jsonData;
                    } else if (actionType.equals("POST") && url.contains("/delete/")) {
                        data = queryString;
                    }
                    if (isRequestSuccessful(response)) {
                        adminActionLogService.insertAdminActionLog(AdminActionLog.builder()
                                .uid(UUID.randomUUID().toString())
                                .adminId(admin.getAdminId())
                                .url(request.getRequestURI())
                                .ip(clientIp)
                                .actionType(actionType)
                                .downloadReason(downloadReason)
                                .actionName(description)
                                .data(data)
                                .adminIndex(admin.getAdminIndex())
                                .detail(koMenu)
                                .build()
                        );
                    }
                }
            }
            log.info("====== AdminInterceptor End ==============");
        }
    }

    private boolean isRequestSuccessful(HttpServletResponse response) {

        int statusCode = response.getStatus();

        if (statusCode >= 200 && statusCode < 300) {
            return true;
        } else
            return false;
    }
}
