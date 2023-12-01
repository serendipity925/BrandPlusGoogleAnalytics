package com.example.BrandPlusGoogleAnalytics.service;

import com.example.BrandPlusGoogleAnalytics.model.GoogleAnalyticsDBModel;
import com.example.BrandPlusGoogleAnalytics.repository.GoogleAuthMyBatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Service
public class GoogleLoadAuthService {

    @Autowired
    private GoogleAuthMyBatisRepository googleAuthMyBatisRepository;

    @Autowired
    private GoogleApiService googleApiService;

    public void googleAnalyticInsert(GoogleAnalyticsDBModel g) {
        googleAuthMyBatisRepository.googleAnalyticInsert(g);
    }

    public void loadAuthToDB(OAuth2AuthenticationToken authentication,String userID){
        OAuth2AuthorizedClient authorizedClient = googleApiService.getAuthorizedClient(authentication);
        Map<String,Object> accAttributes = authentication.getPrincipal().getAttributes();
        String email = accAttributes.get("email").toString();

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
//        String refToken = authorizedClient.getRefreshToken().getTokenValue();
//        System.out.println(refToken);
        String accessTokenExpires = authorizedClient.getAccessToken().getExpiresAt().toString();
        if(googleAuthMyBatisRepository.countByUserId(userID) != 0){
            googleAuthMyBatisRepository.updateAccessToken(userID,accessToken,accessTokenExpires);
        }else{
            googleAuthMyBatisRepository.googleAuthInsert(userID,email,accessToken,accessTokenExpires);
        }
    }
}
