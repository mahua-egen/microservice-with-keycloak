package com.example.springsecuritycommon;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class FeignRequestAuthInterceptor implements RequestInterceptor {
  private Logger logger = LoggerFactory.getLogger(FeignRequestAuthInterceptor.class);

  @Override
  public void apply(RequestTemplate requestTemplate) {
    KeycloakAuthenticationToken keycloakAuthenticationToken =(KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    KeycloakPrincipal keycloakPrincipal =(KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal();
    String tokenString = keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
    String tokenType = keycloakPrincipal.getKeycloakSecurityContext().getToken().getType();
    requestTemplate.header("Authorization", tokenType + " " + tokenString);
  }
}
