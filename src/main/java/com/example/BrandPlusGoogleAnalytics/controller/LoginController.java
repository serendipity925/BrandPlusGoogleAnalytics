package com.example.BrandPlusGoogleAnalytics.controller;

import com.example.BrandPlusGoogleAnalytics.service.GoogleLoadAuthService;
import com.example.BrandPlusGoogleAnalytics.service.GoogleLoadMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginController {

    @Autowired
    private GoogleLoadAuthService googleLoadAuthService;


    @Autowired
    private GoogleLoadMetricService googleLoadMetricService;

    @Autowired
    private LoadMetricController loadMetricController;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/loadToken")
    public boolean loadToken(OAuth2AuthenticationToken authentication, @RequestParam String userID){

        //load authentication info(userID, email, accessToken, accessTokenExpires) into DB
        googleLoadAuthService.loadAuthToDB(authentication, userID);

        //get target metrics data and load into DB
        loadMetricController.loadLast60DaysData(authentication,userID);

        return true;
    }


    //    @GetMapping("/loadRef")
//    public String getRef(OAuth2RefreshToken authenticationToken){
//        OAuth2AuthorizedClient authorizedClient =
//                (OAuth2AuthorizedClient) authenticationToken.getPrincipal();
//
//        return authorizedClient.getRefreshToken().getTokenValue();
//
//    }

}
