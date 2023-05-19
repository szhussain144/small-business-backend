package backend.smallbusiness.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
public class CorsConfiguration {
    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource() {

            @Override
            public org.springframework.web.cors.CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                org.springframework.web.cors.CorsConfiguration corsConfiguration = super.getCorsConfiguration(request);
                String origin = (request.getHeader("origin") != null)?
                        request.getHeader("origin"):"";
                if (origin == "" || origin.startsWith("http://localhost")
                ) {
                    corsConfiguration.setAllowedOrigins(Collections.singletonList(origin));
                }
                return corsConfiguration;
            }

        };
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
