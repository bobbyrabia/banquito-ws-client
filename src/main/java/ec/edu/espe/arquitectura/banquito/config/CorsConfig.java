package ec.edu.espe.arquitectura.banquito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/login")
                        .allowedOrigins("https://striped-reserve-397605.web.app","https://banquito-backoffice.web.app/","https://banquito-bancaweb.web.app/","https://banquito-tellerapp.web.app/","http://localhost:4200")
                        .exposedHeaders("*")
                        .allowedMethods("*");
                registry.addMapping("/api/**")
                        .allowedOrigins("https://striped-reserve-397605.web.app","https://banquito-backoffice.web.app/","https://banquito-bancaweb.web.app/","https://banquito-tellerapp.web.app/","http://localhost:4200")
                        .exposedHeaders("Content-Disposition")
                        .allowedMethods("*");
            }

        };
    }
}
