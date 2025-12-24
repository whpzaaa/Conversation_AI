package org.example.spring_ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置类，允许 localhost:5173 前端端口访问所有后端接口
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 配置跨域规则
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有后端接口生效
                .allowedOrigins("http://localhost:5173") // 明确允许5173端口访问（开发环境）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的HTTP请求方法
                .allowedHeaders("*") // 允许所有请求头（如Content-Type、Authorization等）
                .allowCredentials(true);// 允许携带Cookie/Token等凭证
    }
}