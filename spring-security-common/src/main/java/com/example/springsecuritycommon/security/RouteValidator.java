package com.example.springsecuritycommon.security;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RouteValidator {
  //"/api-docs" for swagger
  public static final List<String> apiUrlList = List.of("/employees","/authentication/token", "/eureka");

  public Predicate<HttpServletRequest> isSecure = serverHttpRequest -> apiUrlList
          .stream().noneMatch(uri -> serverHttpRequest.getRequestURI().contains(uri));
}
