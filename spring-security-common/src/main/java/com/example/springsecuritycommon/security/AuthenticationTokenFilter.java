package com.example.springsecuritycommon.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AddressClaimSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
  private final RouteValidator routeValidator;
  private final CurrentUser currentUser;

  @Autowired
  public AuthenticationTokenFilter(RouteValidator routeValidator, CurrentUser currentUser) {
    this.routeValidator = routeValidator;
    this.currentUser = currentUser;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (routeValidator.isSecure.test(request)) {
      KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) request.getUserPrincipal();
      KeycloakPrincipal keycloakPrincipal = getKeycloakPrincipal(keycloakAuthenticationToken);
      SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken(keycloakPrincipal, null));
    }
    filterChain.doFilter(request, response);
  }

  private KeycloakPrincipal getKeycloakPrincipal(KeycloakAuthenticationToken keycloakAuthenticationToken) {
    KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal();
    getCurrentUser(keycloakAuthenticationToken, keycloakPrincipal);
    return keycloakPrincipal;
  }

  private void getCurrentUser(KeycloakAuthenticationToken keycloakAuthenticationToken, KeycloakPrincipal keycloakPrincipal) {
    AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
//    AddressClaimSet addressClaimSet = accessToken.getAddress();
    currentUser.setId(accessToken.getId());
    currentUser.setFullName(accessToken.getName());
    currentUser.setNickName(accessToken.getNickName());
    currentUser.setFamily_name(accessToken.getFamilyName());
    currentUser.setMiddle_name(accessToken.getMiddleName());
//    currentUser.setAddress(addressClaimSet.getFormattedAddress());
    currentUser.setEmail(accessToken.getEmail());
    currentUser.setGender(accessToken.getGender());
    currentUser.setPhone_number(accessToken.getPhoneNumber());
    currentUser.setBirthdate(accessToken.getBirthdate());
    currentUser.setEnabled(true);
    currentUser.setRoleList(getRoleList(keycloakAuthenticationToken));
  }

  private List<String> getRoleList(KeycloakAuthenticationToken keycloakAuthenticationToken) {
    Collection<GrantedAuthority> authorityList = keycloakAuthenticationToken.getAuthorities();
    return authorityList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
  }
}
