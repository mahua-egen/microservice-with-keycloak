package com.example.oauth2.service;

import com.example.oauth2.dto.Role;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

  @Value("${keycloak.realm}")
  private String realmName;

  private final EmployeeService employeeService;
  private final Keycloak keycloak;

  @Autowired
  public RoleService(EmployeeService employeeService, Keycloak keycloak) {
    this.employeeService = employeeService;
    this.keycloak = keycloak;
  }

  public Role createRole(Role role) {
    RoleRepresentation roleRepresentation = new RoleRepresentation();
    roleRepresentation.setName(role.getName());

    RolesResource rolesResource = getRolesResource();
    rolesResource.create(roleRepresentation);
    return role;
  }

  public Role getRoleByName(String name) {
    RolesResource rolesResource = getRolesResource();
    RoleRepresentation roleRepresentation = rolesResource.get(name).toRepresentation();
    return map(roleRepresentation);
  }

  public void deleteRoleByName(String name) {
    getRolesResource().deleteRole(name);
  }

  public List<Role> getRoleList() {
    List<RoleRepresentation> roleRepresentationList = getRolesResource().list();
    return roleRepresentationList.stream()
            .map(roleRepresentation -> map(roleRepresentation))
            .collect(Collectors.toList());
  }

  public void assignRole(String userId, String roleName) {
    UserResource userResource = employeeService.getUserResource(userId);
    RolesResource rolesResource = getRolesResource();
    RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
    userResource.roles().realmLevel().add(Collections.singletonList(representation));

  }

  private Role map(RoleRepresentation roleRepresentation) {
    return Role.builder()
            .id(roleRepresentation.getId())
            .name(roleRepresentation.getName())
            .build();
  }

  public RolesResource getRolesResource() {
    return keycloak.realm(realmName).roles();
  }
}
