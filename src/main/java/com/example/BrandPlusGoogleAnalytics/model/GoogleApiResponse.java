package com.example.BrandPlusGoogleAnalytics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoogleApiResponse {
    private List<MetricHeader> metricHeaders;
    private List<Row> rows;
    private int rowCount;
    private Metadata metadata;
    private String kind;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MetricHeader {
        private String name;
        private String type;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MetricValue {
        private String value;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Row {
        private List<MetricValue> metricValues;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Metadata {
        private Object schemaRestrictionResponse;
        private String currencyCode;
        private String timeZone;
    }

}
