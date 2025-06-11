package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Cho tất cả URL
                .allowedOrigins("*") // Cho tất cả domain
                .allowedMethods("*") // Cho tất cả phương thức (GET, POST, PUT, DELETE...)
                .allowedHeaders("*"); // Cho tất cả headers
    }
}

