package com.example.BrandPlusGoogleAnalytics.controller;

import com.example.BrandPlusGoogleAnalytics.configuration.GoogleAccConfig;
import com.example.BrandPlusGoogleAnalytics.model.GoogleAccResponse;
import com.example.BrandPlusGoogleAnalytics.service.GoogleAccInfoService;
import com.example.BrandPlusGoogleAnalytics.service.GoogleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class LoadGoogleAccInfoController {

    @Autowired
    private GoogleApiService googleApiService;

    @Autowired
    private GoogleAccInfoService accInfoService;
    @GetMapping("/loadAccSum")
    public GoogleAccResponse loadAccountInfo(OAuth2AuthenticationToken authentication) {

        return accInfoService.getAccInfo(authentication);
    }
}
