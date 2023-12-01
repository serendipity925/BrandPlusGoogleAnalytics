package com.example.BrandPlusGoogleAnalytics.service;

import com.example.BrandPlusGoogleAnalytics.configuration.AppConfig;
import com.example.BrandPlusGoogleAnalytics.configuration.GoogleAuthConfig;
import com.example.BrandPlusGoogleAnalytics.model.GoogleAccResponse;
import com.example.BrandPlusGoogleAnalytics.model.GoogleAnalyticsDBModel;
import com.example.BrandPlusGoogleAnalytics.model.GoogleApiResponse;
import com.example.BrandPlusGoogleAnalytics.repository.GoogleMetricDataMyBatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GoogleLoadMetricService {
    @Autowired
    private GoogleAuthConfig googleAuthConfig;

    @Autowired
    private GoogleApiRequestHeaders requestHeaders;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private GoogleMetricDataMyBatisRepository googleMetricRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private GoogleAccInfoService accInfoService;

    public void loadMetricDB(OAuth2AuthenticationToken authentication,
                             String userID, List<String> customMetrics,
                             List<String> customMetrics1, String customStartDate,
                             String customEndDate, GoogleAccResponse accResponse) {
        //Get property ID and accountName from accResponse
        String propertyID = accResponse.getAccountSummaries().get(0).getPropertySummaries().get(0).getProperty();
        String accountName = accResponse.getAccountSummaries().get(0).getDisplayName();

        //Generate URL by propertyID and make 2 request to get data and store into DB
        String url = googleAuthConfig.getAuthConfig().generateUrl(propertyID);
        String requestBody1 = googleAuthConfig.getAuthConfig().generateRequestBody(customMetrics, customStartDate, customEndDate);
        String requestBody2 = googleAuthConfig.getAuthConfig().generateRequestBody(customMetrics1, customStartDate, customEndDate);

        HttpEntity<String> requestEntity1 = requestHeaders.getHeadersWithRequestBody(authentication, requestBody1);
        HttpEntity<String> requestEntity2 = requestHeaders.getHeadersWithRequestBody(authentication, requestBody2);

        RestTemplate restTemplate1 = appConfig.restTemplate();
        RestTemplate restTemplate2 = appConfig.restTemplate();

        ResponseEntity<GoogleApiResponse> responseEntity1 = restTemplate1.exchange(url, HttpMethod.POST, requestEntity1, GoogleApiResponse.class);
        ResponseEntity<GoogleApiResponse> responseEntity2 = restTemplate2.exchange(url, HttpMethod.POST, requestEntity2, GoogleApiResponse.class);

        GoogleApiResponse responseBody1 = responseEntity1.getBody();
        GoogleApiResponse responseBody2 = responseEntity2.getBody();


        GoogleAnalyticsDBModel googleAnalyticsDBModel = new GoogleAnalyticsDBModel()
                .setUserId(userID)
                .setPropertyId(accountName)
                .setStartDate(dateService.convertToUnixTimeInPDT(customStartDate));

        // Check if data already exists
        Integer count = googleMetricRepository.countByUserIdAndDates(googleAnalyticsDBModel);

        // Set values based on MetricValue list
        setValuesFromMetricValues(googleAnalyticsDBModel, responseBody1.getRows().get(0).getMetricValues(),responseBody2.getRows().get(0).getMetricValues());

        if (count > 0) {
            // Update existing data
            googleMetricRepository.googleAnalyticsUpdate(googleAnalyticsDBModel);
        } else {
            // Insert new data
            googleMetricRepository.googleAnalyticInsert(googleAnalyticsDBModel);
        }
    }

    private void setValuesFromMetricValues(GoogleAnalyticsDBModel model, List<GoogleApiResponse.MetricValue> metricValues, List<GoogleApiResponse.MetricValue> metricValues2) {
        model.setFirstTimePurchasers(Integer.parseInt(metricValues.get(0).getValue()))
                .setItemRevenue(new BigDecimal(metricValues.get(1).getValue()))
                .setItemsPurchased(Integer.parseInt(metricValues.get(2).getValue()))
                .setTotalPurchasers(Integer.parseInt(metricValues.get(3).getValue()))
                .setAveragePurchaseRevenue(new BigDecimal(metricValues2.get(0).getValue()));
    }

}
