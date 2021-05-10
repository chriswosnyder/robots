package com.eventsystem.config;

import com.eventsystem.domain.EventSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@Configuration
public class EventSystemConfig {

    @Value("${EVENT_LIMIT}")
    private Integer limit;


    @Bean
    public EventSystem eventSystem() {
        Map<Long, Integer> eventsPerUser = new WeakHashMap<>();
        Map<Long, Long> timeWhenAUserBecameRobot = new WeakHashMap<>();
        EventSystem eventSystem = new EventSystem(limit, eventsPerUser, timeWhenAUserBecameRobot);
        return eventSystem;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
