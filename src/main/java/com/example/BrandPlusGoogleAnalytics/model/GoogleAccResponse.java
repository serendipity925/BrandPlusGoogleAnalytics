package com.example.BrandPlusGoogleAnalytics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoogleAccResponse {
    private List<AccountSummary> accountSummaries;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class AccountSummary {
        private String name;
        private String account;
        private String displayName;
        private List<PropertySummary> propertySummaries;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PropertySummary {
        private String property;
        private String displayName;
        private String propertyType;
        private String parent;
    }
}
