package com.example.oauth2.controller;

import com.example.oauth2.dto.LoginRequest;
import com.example.oauth2.dto.Oauth2TokenResponse;
import com.example.oauth2.dto.RefreshTokenRequest;
import com.example.oauth2.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/token", produces = {"application/json"})
@AllArgsConstructor
public class LoginController {

  private final LoginService LoginService;

  @PostMapping("/generate")
  public ResponseEntity<Oauth2TokenResponse> generateToken(@RequestBody LoginRequest loginRequest) {
    Oauth2TokenResponse oauth2TokenResponse = LoginService.generateToken(loginRequest);
    return ResponseEntity.ok(oauth2TokenResponse);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    LoginService.logout(refreshTokenRequest);
    return ResponseEntity.ok("logout");
  }
}
