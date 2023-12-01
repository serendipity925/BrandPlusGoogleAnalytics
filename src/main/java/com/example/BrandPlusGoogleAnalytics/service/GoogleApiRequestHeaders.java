package com.example.BrandPlusGoogleAnalytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoogleApiRequestHeaders {
    @Autowired
    private GoogleApiService googleApiService;
    public HttpEntity<String> getHeadersWithRequestBody(OAuth2AuthenticationToken authentication, String requestBody) {

        OAuth2AuthorizedClient authorizedClient = googleApiService.getAuthorizedClient(authentication);
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(requestBody, headers);
    }


    public HttpEntity<String> getHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/json");
        return new HttpEntity<>(headers);
    }


}