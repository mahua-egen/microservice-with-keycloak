package com.example.oauth2.service;

import com.example.oauth2.dto.Group;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GroupService {

  @Value("${keycloak.realm}")
  private String realmName;

  private final EmployeeService employeeService;
  private final RoleService roleService;
  private final Keycloak keycloak;

  @Autowired
  public GroupService(EmployeeService employeeService, RoleService roleService, Keycloak keycloak) {
    this.employeeService = employeeService;
    this.roleService = roleService;
    this.keycloak = keycloak;
  }

  public Group createGroup(Group group) {
    GroupRepresentation groupRepresentation = new GroupRepresentation();
    groupRepresentation.setName(group.getName());

    GroupsResource groupsResource = getGroupsResource();
    Response response = groupsResource.add(groupRepresentation);
    if (Objects.equals(response.getStatus(), 201)) {
      return group;
    }
    throw new RuntimeException("group not created");
  }

  public Group getGroupById(String id) {
    GroupsResource groupsResource = getGroupsResource();
    GroupRepresentation groupRepresentation = groupsResource.group(id).toRepresentation();
    return map(groupRepresentation);
  }

  public List<Group> getGroupList() {
    List<GroupRepresentation> groupRepresentationList = getGroupsResource().groups();
    return groupRepresentationList.stream()
            .map(groupRepresentation -> map(groupRepresentation))
            .collect(Collectors.toList());
  }

  public void assignRoleInGroup( String groupId, List<String> roleNameList) {
    RolesResource rolesResource = roleService.getRolesResource();
    List<RoleRepresentation> roleRepresentationList = rolesResource.list();

    List<RoleRepresentation> matchedRoleRepresentationList = new ArrayList<>();
    for (RoleRepresentation roleRepresentation : roleRepresentationList) {
      for (String roleName : roleNameList) {
        if (roleRepresentation.getName().equals(roleName)) {
          matchedRoleRepresentationList.add(roleRepresentation);
          break;
        }
      }
    }

    GroupsResource groupsResource = getGroupsResource();
    groupsResource.group(groupId).roles().realmLevel().add(matchedRoleRepresentationList);
  }

  public void assignUserToGroup(String userId, List<String> leaveGroupIdList, List<String> addGroupIdList) {
    UserResource userResource = employeeService.getUserResource(userId);
    if(Objects.nonNull(leaveGroupIdList) && leaveGroupIdList.size()>0) {
      leaveGroupIdList.stream().forEach(groupId -> userResource.leaveGroup(groupId));
    }
    if(Objects.nonNull(addGroupIdList) && addGroupIdList.size()>0) {
      addGroupIdList.stream().forEach(groupId -> userResource.joinGroup(groupId));
    }
  }

  private Group map(GroupRepresentation groupRepresentation) {
    return Group.builder()
            .id(groupRepresentation.getId())
            .name(groupRepresentation.getName())
            .build();
  }

  public GroupsResource getGroupsResource() {
    RealmResource realmResource = keycloak.realm(realmName);
    return realmResource.groups();
  }
}
