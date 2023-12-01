package com.example.BrandPlusGoogleAnalytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GoogleApiService {
    @Autowired
    private RestTemplate restTemplate;

    private final OAuth2AuthorizedClientService authorizedClientService;

    public GoogleApiService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    public OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return  this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
        );
    }

    public Map<String, Object> getUserInfo(String userinfoEndpoint) {
        return restTemplate.getForObject(userinfoEndpoint, Map.class);
    }
}
