package com.example.oauth2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
  private String id;
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private List<String> clientRoles;
  private Boolean enabled;
}
