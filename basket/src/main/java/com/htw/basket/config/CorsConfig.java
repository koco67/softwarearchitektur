package com.htw.basket.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/basket/**") 
                .allowedOrigins("http://localhost:3000") // Erlaubte Ursprünge (Domains)
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Erlaubte HTTP-Methoden
    }
}
