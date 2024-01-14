package pt.ipleiria.careline.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pt.ipleiria.careline.services.impl.RateLimitingService;

@Configuration
public class Bucket4jRateLimitApp implements WebMvcConfigurer {

    private final RateLimitingService rateLimitingService;

    @Autowired
    public Bucket4jRateLimitApp(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    public RateLimitInterceptor rateLimitInterceptor() {
        return new RateLimitInterceptor(rateLimitingService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor())
                .addPathPatterns("/api/patients/*/**", "/api/professionals/*/**");
    }
}
