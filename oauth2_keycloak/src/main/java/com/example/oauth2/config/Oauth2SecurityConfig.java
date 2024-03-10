package com.example.oauth2.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.client.RestTemplate;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Import(KeycloakSpringBootConfigResolver.class)
public class Oauth2SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
  /**
   * Registers the KeycloakAuthenticationProvider with the authentication manager.
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    KeycloakAuthenticationProvider authenticationProvider = new KeycloakAuthenticationProvider();
    authenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(authenticationProvider);
  }

  /**
   * Defines the session authentication strategy.
   */
  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy(); // NullAuthenticatedSessionStrategy for bearer-only applications
//    return new RegisterSessionAuthenticationStrategy(buildSessionRegistry()); // RegisterSessionAuthenticationStrategy for public or confidential applications
  }

  @Bean
  protected SessionRegistry buildSessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.cors()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/authentication/employee/save").permitAll()
            .antMatchers(HttpMethod.POST, "/authentication/token/generate").permitAll()
            .antMatchers(HttpMethod.GET, "/authentication/employee/list").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/authentication/employee/update").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/authentication/employee/byId?id=*").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/authentication/employee/byUsername?username=*").hasRole("USER")
            .antMatchers(HttpMethod.DELETE, "/authentication/employee?id=*").hasRole("ADMIN")
            .antMatchers("/authentication/role*").hasRole("ADMIN")
            .antMatchers("/authentication/group*").hasRole("ADMIN")
            .anyRequest().permitAll();
  }

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
