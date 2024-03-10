package com.example.oauth2.config;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class KeycloakAdminApiConfig {

  @Value("${keycloak.realm}")
  private String realm;

  @Value("${keycloak.resource}")
  private String resource;

  @Value("${keycloak.auth-server-url}")
  private String authServerUrl;

  @Value("${keycloak.credentials.secret}")
  private String secret;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
            .serverUrl(authServerUrl)
            .realm(realm)
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .clientId(resource)
            .clientSecret(secret)
            .build();
  }
}
