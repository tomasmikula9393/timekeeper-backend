package home.tm.configuration;

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
                registry.addMapping("/**") // Povolit všechny cesty
                        .allowedOrigins("http://localhost:3000",
                                "https://timekeeper-tm.onrender.com") // Povolit front-end hostitele
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Povolené HTTP metody
                        .allowedHeaders("*") // Povolit všechny hlavičky
                        .allowCredentials(true)
                        .exposedHeaders("Authorization")
                        .maxAge(3600); // Cache preflight odpovědi na 1 hodinu
            }
        };
    }
}
