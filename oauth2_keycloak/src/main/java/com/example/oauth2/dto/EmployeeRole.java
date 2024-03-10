package com.example.oauth2.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRole {
  private String userId;
  private String roleName;
}
