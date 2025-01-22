package com.skn.admin.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashSet;
import java.util.Set;

public class CustomInterceptor implements HandlerInterceptor {

    private Set<RequestKey> handledRequests = new HashSet<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Multipart 요청인지 확인하여 두 번째 요청이면 처리하지 않음
        if (request instanceof MultipartHttpServletRequest) {
            String url = request.getRequestURI();
            String method = request.getMethod();
            RequestKey requestKey = new RequestKey(url, method);

            if (handledRequests.contains(requestKey)) {
                // 이미 처리한 요청이므로 중복 로깅을 방지
                return false;
            }

            handledRequests.add(requestKey);
        }

        // 그 외 로직 처리
        // (생략)

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청 처리가 완료된 후에 요청을 처리한 키를 제거하여 다음 요청을 처리할 수 있도록 함
        if (request instanceof MultipartHttpServletRequest) {
            String url = request.getRequestURI();
            String method = request.getMethod();
            RequestKey requestKey = new RequestKey(url, method);

            handledRequests.remove(requestKey);
        }

        // 그 외 로직 처리
        // (생략)
    }
}
