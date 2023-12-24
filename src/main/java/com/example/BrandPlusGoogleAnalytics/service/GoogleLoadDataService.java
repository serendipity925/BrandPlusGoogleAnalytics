package com.example.BrandPlusGoogleAnalytics.service;

import com.example.BrandPlusGoogleAnalytics.configuration.AppConfig;
import com.example.BrandPlusGoogleAnalytics.configuration.GoogleAuthConfig;
import com.example.BrandPlusGoogleAnalytics.model.GoogleAccResponse;
import com.example.BrandPlusGoogleAnalytics.model.GoogleApiResponse;
import com.example.BrandPlusGoogleAnalytics.repository.GoogleMetricDataMyBatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GoogleLoadDataService {
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

    public String loadAdMetrics(OAuth2AuthenticationToken authentication,
                             String userID, List<String> customMetrics,
                             List<String> customDimensions, String customStartDate,
                             String customEndDate, GoogleAccResponse accResponse) {
        //Get property ID and accountName from accResponse
        String propertyID = accResponse.getAccountSummaries().get(0).getPropertySummaries().get(0).getProperty();
        String accountName = accResponse.getAccountSummaries().get(0).getDisplayName();

        //Generate URL by propertyID and make 2 request to get data and store into DB
        String url = googleAuthConfig.getAuthConfig().generateUrl(propertyID);
        String requestBody = googleAuthConfig.getAuthConfig().generateRequestBody2(customMetrics, customDimensions, customStartDate, customEndDate);

        HttpEntity<String> requestEntity = requestHeaders.getHeadersWithRequestBody(authentication, requestBody);

        RestTemplate restTemplate = appConfig.restTemplate();

        ResponseEntity<GoogleApiResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, GoogleApiResponse.class);

        GoogleApiResponse responseBody = responseEntity.getBody();

        System.out.println(responseBody.toString());

        return responseBody.toString();
    }

}
