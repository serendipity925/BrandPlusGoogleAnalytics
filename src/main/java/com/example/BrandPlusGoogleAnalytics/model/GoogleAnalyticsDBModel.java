package com.example.BrandPlusGoogleAnalytics.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)

public class GoogleAnalyticsDBModel {
    private String userId;
    private String propertyId;
    private Long startDate;
    private Long endDate;
    private Integer firstTimePurchasers;
    private BigDecimal itemRevenue;
    private BigDecimal averagePurchaseRevenue;
    private Integer itemsPurchased;
    private Integer totalPurchasers;

}
