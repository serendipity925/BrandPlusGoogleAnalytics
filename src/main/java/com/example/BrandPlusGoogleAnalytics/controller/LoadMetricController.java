package com.example.BrandPlusGoogleAnalytics.controller;

import com.example.BrandPlusGoogleAnalytics.model.GoogleAccResponse;
import com.example.BrandPlusGoogleAnalytics.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class LoadMetricController {
    @Autowired
    private GoogleLoadAuthService googleLoadAuthService;

    @Autowired
    private GoogleLoadMetricService googleLoadMetricService;

    @Autowired
    private DateService dateService;

    @Autowired
    private GoogleAccInfoService accInfoService;

    @Autowired
    private GoogleLoadDataService loadDataService;


    @GetMapping("/loadOneDay")
    public boolean loadOneDayData(OAuth2AuthenticationToken authentication, @RequestParam String userID){


        //get target metrics data and load into DB

        List<String> customMetrics = new ArrayList<>();
        customMetrics.add("firstTimePurchasers");
        customMetrics.add("itemRevenue");
        customMetrics.add("itemsPurchased");
        customMetrics.add("totalPurchasers");

        List<String> customMetrics2 = new ArrayList<>();
        customMetrics2.add("averagePurchaseRevenue");

        String endDate = dateService.getFirstEndDayInPDT();
        String startDate = dateService.getPreviousDayInPDT(endDate);

        GoogleAccResponse accResponse = accInfoService.getAccInfo(authentication);

        googleLoadMetricService.loadMetricDB(authentication, userID, customMetrics,customMetrics2, startDate, endDate, accResponse);

        return true;
    }


    @GetMapping("/loadLast60Days")
    public boolean loadLast60DaysData(OAuth2AuthenticationToken authentication, @RequestParam String userID) {
        List<String> customMetrics = new ArrayList<>();
        customMetrics.add("firstTimePurchasers");
        customMetrics.add("itemRevenue");
        customMetrics.add("itemsPurchased");
        customMetrics.add("totalPurchasers");

        List<String> customMetrics2 = new ArrayList<>();
        customMetrics2.add("averagePurchaseRevenue");

        String endDate = dateService.getFirstEndDayInPDT();

        GoogleAccResponse accResponse = accInfoService.getAccInfo(authentication);

        // Load data for the last 90 days, one day at a time
        for (int i = 0; i < 90; i++) {
            String startDate = dateService.getPreviousDayInPDT(endDate);
            googleLoadMetricService.loadMetricDB(authentication, userID, customMetrics,customMetrics2, startDate, endDate, accResponse);
            endDate = startDate;
        }

        return true;
    }

    @GetMapping("/getAdMetrics")
    public boolean getAdMetrics(OAuth2AuthenticationToken authentication, @RequestParam String userID) {
        List<String> adMetrics = Arrays.asList(
                "advertiserAdClicks",
                "advertiserAdCost",
                "advertiserAdCostPerConversion",
                "advertiserAdImpressions",
                "returnOnAdSpend"
        );

        List<String> adDimensions = Arrays.asList(
                "medium",
                "source",
                "sourceMedium",
                "sourcePlatform"
        );

        String endDate = dateService.getFirstEndDayInPDT();

        GoogleAccResponse accResponse = accInfoService.getAccInfo(authentication);

        String startDate = dateService.getPrevious30DayInPDT(endDate);
        loadDataService.loadAdMetrics(authentication, userID, adMetrics,adDimensions, startDate, endDate, accResponse);

        return true;
    }


    @GetMapping("/getSalesMetrics")
    public boolean getSalesMetrics(OAuth2AuthenticationToken authentication, @RequestParam String userID) {
        List<String> salesMetrics = Arrays.asList(
                "averagePurchaseRevenue",
                "averagePurchaseRevenuePerUser",
                "purchaserConversionRate",
                "purchaseRevenue",
                "sessionConversionRate",
                "totalAdRevenue",
                "totalPurchasers",
                "totalRevenue",
                "transactions",
                "userConversionRate"
        );

        List<String> salesDimensions = Arrays.asList("date");

        String endDate = dateService.getFirstEndDayInPDT();

        GoogleAccResponse accResponse = accInfoService.getAccInfo(authentication);

        String startDate = dateService.getPrevious30DayInPDT(endDate);
        loadDataService.loadAdMetrics(authentication, userID, salesMetrics,salesDimensions, startDate, endDate, accResponse);

        return true;
    }

    @GetMapping("/getAudienceMetrics")
    public boolean getAudienceMetrics(OAuth2AuthenticationToken authentication, @RequestParam String userID) {
        List<String> audienceMetrics = Arrays.asList(
                "averageSessionDuration",
                "bounceRate",
                "newUsers",
                "screenPageViews",
                "sessions",
                "totalPurchasers",
                "totalUsers"
        );

        List<String> audienceDimensions = Arrays.asList(
                "city",
                "continent",
                "country",
                "eventName",
                "language",
                "userAgeBracket",
                "userGender"
        );

        String endDate = dateService.getFirstEndDayInPDT();

        GoogleAccResponse accResponse = accInfoService.getAccInfo(authentication);

        String startDate = dateService.getPrevious30DayInPDT(endDate);
        loadDataService.loadAdMetrics(authentication, userID, audienceMetrics,audienceDimensions, startDate, endDate, accResponse);

        return true;
    }



}
