package com.example.BrandPlusGoogleAnalytics.controller;

import com.example.BrandPlusGoogleAnalytics.model.GoogleAccResponse;
import com.example.BrandPlusGoogleAnalytics.service.DateService;
import com.example.BrandPlusGoogleAnalytics.service.GoogleAccInfoService;
import com.example.BrandPlusGoogleAnalytics.service.GoogleLoadAuthService;
import com.example.BrandPlusGoogleAnalytics.service.GoogleLoadMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

}
