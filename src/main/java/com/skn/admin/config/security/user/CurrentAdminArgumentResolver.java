package com.skn.admin.config.security.user;

import com.skn.admin.environment.dto.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by 박현진.
 * Date: 2023-02-21
 * Description: 로그인 Manager 객체 어노테이션 사용을 위한 Resolver
 */

@Component
@RequiredArgsConstructor
public class CurrentAdminArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isCurrentUserAnnotation = parameter.getParameterAnnotation(CurrentAdmin.class) != null;
        boolean isUserClass = Admin.class.equals(parameter.getParameterType());

        return isCurrentUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Admin admin = null;
        try {
            admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            admin = null;
        }
        return admin;
    }
}
