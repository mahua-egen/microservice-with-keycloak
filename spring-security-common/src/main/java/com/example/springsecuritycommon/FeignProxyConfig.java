package com.example.springsecuritycommon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignProxyConfig {

  @Bean
  FeignRequestAuthInterceptor authFeign() {
    return new FeignRequestAuthInterceptor();
  }
}
