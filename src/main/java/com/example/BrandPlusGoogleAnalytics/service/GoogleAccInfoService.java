package com.example.BrandPlusGoogleAnalytics.service;

import com.example.BrandPlusGoogleAnalytics.configuration.AppConfig;
import com.example.BrandPlusGoogleAnalytics.configuration.GoogleAccConfig;
import com.example.BrandPlusGoogleAnalytics.model.GoogleAccResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleAccInfoService {
    @Autowired
    private GoogleAccConfig accConfig;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private GoogleApiRequestHeaders requestHeaders;

    @Autowired
    private GoogleApiService googleApiService;

    public GoogleAccResponse getAccInfo(OAuth2AuthenticationToken authentication) {
        String url = accConfig.getAccConfig().generateUrl();
        OAuth2AuthorizedClient authorizedClient = googleApiService.getAuthorizedClient(authentication);
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        HttpEntity<String> requestEntity = requestHeaders.getHeaders(accessToken);

        RestTemplate restTemplate = appConfig.restTemplate();

        ResponseEntity<GoogleAccResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, GoogleAccResponse.class);

        return responseEntity.getBody();
    }
}
