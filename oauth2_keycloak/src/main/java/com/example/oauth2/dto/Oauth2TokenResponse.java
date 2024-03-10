package com.example.oauth2.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oauth2TokenResponse {
  private String token;
  private String refreshToken;
}
