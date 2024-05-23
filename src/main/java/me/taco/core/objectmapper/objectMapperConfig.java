package me.taco.core.objectmapper;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class objectMapperConfig {
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
