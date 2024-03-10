package com.example.oauth2.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
  private String id;
  private String name;
}
