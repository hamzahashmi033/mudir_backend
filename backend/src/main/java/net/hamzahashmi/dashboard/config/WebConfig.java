package net.hamzahashmi.dashboard.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000","https://mudir-dashboard.vercel.app")  // Allow your frontend domain
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed methods
                        .allowedHeaders("*")  // Allow all headers
                        .allowCredentials(true); // Allow cookies/auth credentials if needed
            }
        };
    }
}