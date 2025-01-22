package com.skn.admin.config.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: Authentication Success, Fail Handler 에서 상속하여 사용하는 인터페이스
 */

public interface DefaultAuthenticationHandler {
    default void writeResponse(HttpServletRequest request, HttpServletResponse response, Exception exception, String message, Map<String, Object> result) {
        if (exception != null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        result.put("type", (exception != null) ? exception.getClass().getName() : "");
        writeJson(response, result);
    }
    public static void writeJson(HttpServletResponse response, Map<?, ?> data) {
        writeJson(response, stringify(data));
    }

    public static ObjectMapper getObjectMapper() {

        ObjectMapper o = new ObjectMapper();
        o.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        o.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        o.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        return o;
    }

    public static String stringify(Object o) {
        try {
            return getObjectMapper().writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeJson(HttpServletResponse response, String data) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        write(response, data);
    }

    public static void write(HttpServletResponse response, String data) {
        try {

            String contentType = response.getContentType();

            if (StringUtils.isBlank(contentType)) {
                response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            }

            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println(data);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
