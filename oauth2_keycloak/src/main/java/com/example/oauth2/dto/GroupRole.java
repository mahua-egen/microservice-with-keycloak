package com.example.oauth2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupRole {
  private String groupId;
  private List<String> roleNameList;
}
