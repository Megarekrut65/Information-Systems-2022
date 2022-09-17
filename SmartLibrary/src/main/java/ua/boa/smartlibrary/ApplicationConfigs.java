package ua.boa.smartlibrary;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfigs implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(FRONTEND_URL).allowedMethods("GET", "POST", "PUT");
    }
    private final static String FRONTEND_URL = "http://127.0.0.1:5500";
}
