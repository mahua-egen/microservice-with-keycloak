package com.example.oauth2.service;

import com.example.oauth2.dto.LoginRequest;
import com.example.oauth2.dto.Oauth2TokenResponse;
import com.example.oauth2.dto.RefreshTokenRequest;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {

  @Value("${keycloak.realm}")
  private String realm;

  @Value("${keycloak.resource}")
  private String clientId;

  @Value("${keycloak.auth-server-url}")
  private String authServerUrl;

  @Value("${keycloak.credentials.secret}")
  private String clientSecret;

  @Autowired
  private RestTemplate restTemplate;

  public Oauth2TokenResponse generateToken(LoginRequest loginRequest) {
    AccessTokenResponse accessTokenResponse = Keycloak.getInstance(authServerUrl, realm, loginRequest.getUsername(),
                    loginRequest.getPassword(), clientId, clientSecret)
            .tokenManager().getAccessToken();



    return Oauth2TokenResponse.builder()
            .token(accessTokenResponse.getToken())
            .refreshToken(accessTokenResponse.getRefreshToken())
            .build();
  }

  public void logout(RefreshTokenRequest refreshTokenRequest) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(prepareLogoutRequestMultiValueMap(refreshTokenRequest), headers);
    String url = String.format("%s/realms/%s/protocol/openid-connect/logout", this.authServerUrl, this.realm);

    restTemplate.postForEntity(url, request, Object.class);

  }

  private MultiValueMap<String, String> prepareLogoutRequestMultiValueMap(RefreshTokenRequest refreshTokenRequest) {
    MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
    requestMap.add("client_id", clientId);
    requestMap.add("client_secret", clientSecret);
    requestMap.add("refresh_token", refreshTokenRequest.getRefreshToken());
    return requestMap;
  }


}


