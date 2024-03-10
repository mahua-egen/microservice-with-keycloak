package com.example.oauth2.service;

import com.example.oauth2.dto.Employee;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

  @Value("${keycloak.realm}")
  private String realmName;

  private final Keycloak keycloak;

  @Autowired
  public EmployeeService(Keycloak keycloak) {
    this.keycloak = keycloak;
  }

  public Employee createEmployee(Employee employee) {
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setEnabled(true);
    userRepresentation.setFirstName(employee.getFirstName());
    userRepresentation.setLastName(employee.getLastName());
    userRepresentation.setUsername(employee.getUsername());
    userRepresentation.setEmail(employee.getEmail());
    userRepresentation.setEmailVerified(true);

    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setValue(employee.getPassword());
    credential.setTemporary(false);
    credential.setType(CredentialRepresentation.PASSWORD);
    userRepresentation.setCredentials(List.of(credential));

    UsersResource userResource = getUsersResource();

    Response response = userResource.create(userRepresentation);
    if (Objects.equals(response.getStatus(), 201)) {
      return employee;
    }
    throw new RuntimeException("Employee not created");
  }

  public Employee updateEmployeeProfile(Employee employee) {
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setId(employee.getId());
    userRepresentation.setEnabled(true);
    userRepresentation.setFirstName(employee.getFirstName());
    userRepresentation.setLastName(employee.getLastName());
    userRepresentation.setEmail(employee.getEmail());
    userRepresentation.setEmailVerified(true);

    List<String> actionList = new ArrayList<>();
    actionList.add("UPDATE_PROFILE");

    UsersResource userResource = getUsersResource();

    Response response = userResource.create(userRepresentation);
    if (Objects.equals(response.getStatus(), 201)) {
      return employee;
    }
    throw new RuntimeException("Employee not created");
  }

  public void emailVerification(String employeeId) {
    UsersResource usersResource = getUsersResource();
    usersResource.get(employeeId).sendVerifyEmail();
  }

  public void updateEmployeePassword(String employeeId) {
    UsersResource usersResource = getUsersResource();
    List<String> actionList = new ArrayList<>();
    actionList.add("UPDATE_PASSWORD");
    usersResource.get(employeeId).executeActionsEmail(actionList);
  }

  public void resetEmployeePassword(String username) {
    UsersResource usersResource = getUsersResource();
    List<UserRepresentation> userRepresentationList = usersResource.searchByUsername(username, true);
    UserRepresentation userRepresentation = userRepresentationList.stream().findFirst().orElse(null);
    if (Objects.nonNull(userRepresentation)) {
      List<String> actionList = new ArrayList<>();
      actionList.add("UPDATE_PASSWORD");
      usersResource.get(userRepresentation.getId()).executeActionsEmail(actionList);
      return;
    }
    throw new RuntimeException("username not found");
  }

  public Employee getEmployeeById(String id) {
    UsersResource usersResource = getUsersResource();
    UserRepresentation userRepresentation = usersResource.get(id).toRepresentation();
    return map(userRepresentation);
  }

  public Employee getEmployeeByUsername(String username) {
    UsersResource usersResource = getUsersResource();
    List<UserRepresentation> userRepresentationList = usersResource.searchByUsername(username, true);
    UserRepresentation userRepresentation = userRepresentationList.stream().findFirst().orElse(null);
    if (Objects.nonNull(userRepresentation)) {
      return map(userRepresentation);
    }
    return null;
  }

  public void deleteEmployeeById(String id) {
    Response response = getUsersResource().delete(id);
    if (!Objects.equals(response.getStatus(), 200)) {
      throw new RuntimeException("employee not deleted...");
    }
  }

  public List<Employee> getEmployeeList() {
    List<UserRepresentation> userRepresentationList = getUsersResource().list();
    return userRepresentationList.stream()
            .map(userRepresentation -> map(userRepresentation))
            .collect(Collectors.toList());
  }

  public UsersResource getUsersResource() {
    RealmResource realmResource = keycloak.realm(realmName);
    return realmResource.users();
  }

  public UserResource getUserResource(String userId) {
    UsersResource usersResource = getUsersResource();
    return usersResource.get(userId);
  }


  private Employee map(UserRepresentation userRepresentation) {
    return Employee.builder()
            .id(userRepresentation.getId())
            .username(userRepresentation.getUsername())
            .firstName(userRepresentation.getFirstName())
            .lastName(userRepresentation.getLastName())
            .email(userRepresentation.getEmail())
            .enabled(userRepresentation.isEnabled())
            .build();
  }
}
