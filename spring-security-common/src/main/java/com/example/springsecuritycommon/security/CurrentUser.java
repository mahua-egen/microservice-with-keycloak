package com.example.springsecuritycommon.security;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentUser {
  private String id;
  private String fullName;
  private String nickName;
  private String family_name;
  private String middle_name;
  private String email;
  private String gender;
  private String phone_number;
  private String birthdate;
  private String address;
  private Boolean enabled;
  private List<String> roleList;
}
