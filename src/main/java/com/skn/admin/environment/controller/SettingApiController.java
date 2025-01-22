package com.skn.admin.environment.controller;

import com.skn.admin.environment.service.SettingService;
import com.skn.admin.util.NTResult;
import com.skn.admin.util.NTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SettingApiController {
    private final SettingService settingService;

    @PostMapping(value = "/api/v1/environment/setting")
    public ResponseEntity createSetting(HttpServletRequest request, Model model) throws Exception {
        NTResult result = new NTResult();
        try {
            // 비밀번호 변경 안내 설정
            String isUsePW = (NTUtil.isNull(request.getParameter("isUsePW"),"").equals("Y") ? "Y" : "N");
            String pwPeriod = "";
            String pwPeriodNext = "";
            if(isUsePW.equals("Y")) {
                pwPeriod = NTUtil.isNull(request.getParameter("pwPeriod"),"180");
                pwPeriodNext = NTUtil.isNull(request.getParameter("pwPeriodNext"),"7");
            }

            // 자동 로그아웃 설정
            String isUseLifetime = (NTUtil.isNull(request.getParameter("isUseLifetime"),"").equals("Y") ? "Y" : "N");
            String lifetime = "";
            if (isUseLifetime.equals("Y")){
                lifetime = NTUtil.isNull(request.getParameter("lifetime"),"120");
            }

            // ip 접속 제한 설정
            String isUseIP = (NTUtil.isNull(request.getParameter("isUseIP"),"").equals("Y") ? "Y" : "N");
            String ip = "";
            String ipAll = "";
            if (isUseIP.equals("Y")) {
                String[] ip1List = request.getParameterValues("ip1");
                String[] ip2List = request.getParameterValues("ip2");
                String[] ip3List = request.getParameterValues("ip3");

                String[] ipStartList = request.getParameterValues("ipStart");
                String[] ipEndList = request.getParameterValues("ipEnd");

                if(ip1List != null) {
                    for (int i = 0;i < ip1List.length;i++)
                    {
                        String ip1 = NTUtil.isNull(ip1List[i]);
                        String ip2 = NTUtil.isNull(ip2List[i]);
                        String ip3 = NTUtil.isNull(ip3List[i]);

                        String ipStart = NTUtil.isNull(ipStartList[i]);

                        if (ip1 != "" && ip2 != "" && ip3 != "" && ipStart != "") {
                            String ipEnd = NTUtil.isNull(ipEndList[i]);
                            if (ipEnd == "") ipEnd = ipStart;

                            try {
                                for (int j = Integer.parseInt(ipStart);j <= Integer.parseInt(ipEnd);j++)
                                {
                                    ipAll += ip1 + '.' + ip2 + '.' + ip3 + '.' + j + ';';
                                }
                            } catch (Exception e) {
                                break;
                            }
                            ip += ip1 + '.' + ip2 + '.' + ip3 + '.' + ipStart + '~' + ipEnd + ';';

                        }
                    }
                }
            }

            Map map = new HashMap();
            map.put("isUsePw", isUsePW);
            map.put("pwPeriod", pwPeriod);
            map.put("pwPeriodNext", pwPeriodNext);
            map.put("isUseLifetime", isUseLifetime);
            map.put("lifetime", lifetime);
            map.put("isUseIp", isUseIP);
            map.put("siteIp", ip);
            map.put("siteIpAll", ipAll);
            map.put("adminIndex", request.getSession().getAttribute("adminIndex"));
            int results = settingService.insertSetting(map);
            result.setSuccess();
        } catch (Exception e1) {
            result.setFail();
        }

        return ResponseEntity.ok().body(result);
    }
}
