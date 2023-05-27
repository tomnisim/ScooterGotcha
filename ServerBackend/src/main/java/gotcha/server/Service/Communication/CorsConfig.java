package gotcha.server.Service.Communication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new UrlBasedCorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                String origin = request.getHeader("Origin");
                if (origin != null && (origin.startsWith("http://79.178.130.112") || origin.startsWith("http://localhost"))) {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.addAllowedHeader("Access-Control-Request-Method");
                    corsConfiguration.addAllowedHeader("Content-Type");
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                }
                return null;
            }
        };
    }
}

