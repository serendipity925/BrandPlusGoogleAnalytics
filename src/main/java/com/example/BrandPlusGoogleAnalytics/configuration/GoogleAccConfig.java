package com.example.BrandPlusGoogleAnalytics.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GoogleAccConfig {

    @Bean
    @ConfigurationProperties(prefix="google-account-endpoint")
    public AccConfig getAccConfig(){
        return new AccConfig();
    }

    @Data
    public static class AccConfig {
        private String base;
        private String key;
        private boolean keepEmptyRows;

        public String generateUrl() {
            return base + key;
        }
    }
}
