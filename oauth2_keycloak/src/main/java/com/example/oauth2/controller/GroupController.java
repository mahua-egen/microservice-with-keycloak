package com.example.oauth2.controller;

import com.example.oauth2.dto.Group;
import com.example.oauth2.dto.GroupRole;
import com.example.oauth2.dto.UserGroup;
import com.example.oauth2.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/group", produces = {"application/json"})
@AllArgsConstructor
public class GroupController {
  private final GroupService groupService;

  @PostMapping("/save")
  public ResponseEntity<Group> createGroup(@RequestBody Group group) {
    group = groupService.createGroup(group);
    return ResponseEntity.ok(group);
  }

  @GetMapping()
  public ResponseEntity<Group> getGroupById(@RequestParam("id") String id) {
    Group group = groupService.getGroupById(id);
    return ResponseEntity.ok(group);
  }

  @GetMapping("/list")
  public ResponseEntity<List<Group>> getGroupList() {
    List<Group> groupList = groupService.getGroupList();
    return ResponseEntity.ok(groupList);
  }

  @PostMapping("/assign-role-group")
  public ResponseEntity<String> assignRoleGroup(@RequestBody GroupRole groupRole) {
    groupService.assignRoleInGroup(groupRole.getGroupId(), groupRole.getRoleNameList());
    return ResponseEntity.ok("role-group mapping is successful");
  }

  @PostMapping("/assign-user-group")
  public ResponseEntity<String> assignUserGroup(@RequestBody UserGroup userGroup) {
    groupService.assignUserToGroup(userGroup.getUserId(), userGroup.getLeaveGroupIdList(), userGroup.getAddGroupIdList());
    return ResponseEntity.ok("user-group mapping is successful");
  }
}
