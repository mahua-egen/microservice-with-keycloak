package com.example.oauth2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroup {
  private String userId;
  private List<String> leaveGroupIdList;
  private List<String> addGroupIdList;
}
