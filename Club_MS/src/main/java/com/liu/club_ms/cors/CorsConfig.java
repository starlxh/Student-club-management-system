package com.liu.club_ms.cors;

import com.liu.club_ms.intercepter.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
//  重写WebMvcConfigurer，设置全局跨域
    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .maxAge(3600L);
            }
        };
    }

//  注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir =
                registry.addInterceptor(new LoginInterceptor());
        //添加拦截内容
        ir.addPathPatterns("/**");
        //添加不拦截内容
        ir.excludePathPatterns("/login/loginIn", "/login/loginOut", "/login/sendCaptcha");
    }
}
