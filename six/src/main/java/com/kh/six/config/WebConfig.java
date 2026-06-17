package com.kh.six.config;

import com.kh.six.interceptor.AuthInterceptor;
import com.kh.six.interceptor.LoginPageBlockInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 로그인 페이지 접근 차단
        registry.addInterceptor(new LoginPageBlockInterceptor())
                .addPathPatterns(
                        "/member/login",
                        "/member/join",
                        "/employee/login",
                        "/admin/login"
                );

        // 권한 체크
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns(
                        "/**/admin/**",
                        "/**/employee/**",
                        "/**/mypage/**"
                )
                .excludePathPatterns(
                        "/",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/member/login",
                        "/employee/login",
                        "/admin/login"
                );
    }
}
