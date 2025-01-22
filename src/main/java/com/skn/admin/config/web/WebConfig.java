package com.skn.admin.config.web;

import com.skn.admin.common.interceptor.AdminInterceptor;
import com.skn.admin.common.interceptor.CrudInterceptor;
import com.skn.admin.common.interceptor.CustomInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Created by 박현진.
 * Date: 2023-02-16
 * Description: Web 환경설정
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * MethodInterceptor 적용
     */


    @Bean
    public Advisor crudInterceptorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* com.skn.admin..*Controller.*(..))");
        return new DefaultPointcutAdvisor(pointcut, new CrudInterceptor());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 정적파일 인터셉터 제외
        registry.addInterceptor(adminInterceptor())
                .excludePathPatterns("/assets/**")
                .excludePathPatterns("/api/v1/file/**");

        // method Interceptor 추가
//        registry.addInterceptor(new MethodInterceptor());
//        .excludePathPatterns("/api/**");

        // 두 번째 인터셉터 등록
        registry.addInterceptor(customInterceptor())
                .addPathPatterns("/upload/**"); // 파일 업로드 관련 URL 패턴


        // Logging Interceptor 등록
//        registry.addInterceptor(loggingInterceptor);
//
    }




    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

    @Bean CustomInterceptor customInterceptor() {
        return new CustomInterceptor();
    }

    /**
     * JSON View
     */
    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }



}
