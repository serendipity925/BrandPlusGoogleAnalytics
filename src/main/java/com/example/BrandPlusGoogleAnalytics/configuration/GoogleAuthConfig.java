package com.example.BrandPlusGoogleAnalytics.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GoogleAuthConfig {

    @Bean
    @ConfigurationProperties(prefix="google-auth-endpoint")
    public GoogleConfig getAuthConfig(){
        return new GoogleConfig();
    }

    @Data
    public static class GoogleConfig {
        private String base;
        private String key;
        private boolean keepEmptyRows;

        // Method to generate the URL with custom start and end dates
        public String generateUrl(String propertyId) {
            return base + propertyId + key;
        }

        // Method to generate the request body with custom metrics, start date, and end date
        public String generateRequestBody(List<String> customMetrics, String customStartDate, String customEndDate) {
            StringBuilder requestBody = new StringBuilder("{"
                    + "\"metrics\":[");
            for (String metric : customMetrics) {
                requestBody.append("{\"name\":\"").append(metric).append("\"},");
            }
            // Remove the last comma
            if (!customMetrics.isEmpty()) {
                requestBody.deleteCharAt(requestBody.length() - 1);
            }
            requestBody.append("],"
                    + "\"dateRanges\":["
                    + "{\"startDate\":\"").append(customStartDate).append("\",\"endDate\":\"").append(customEndDate).append("\"}],"
                    + "\"keepEmptyRows\":true"
                    + "}");

            return requestBody.toString();
        }
    }
}
