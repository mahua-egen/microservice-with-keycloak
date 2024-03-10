package com.example.oauth2.controller;

import com.example.oauth2.dto.EmployeeRole;
import com.example.oauth2.dto.Role;
import com.example.oauth2.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/role", produces = {"application/json"})
@AllArgsConstructor
public class RoleController {
  private final RoleService roleService;

  @PostMapping("/save")
  public ResponseEntity<Role> createRole(@RequestBody Role role) {
    role = roleService.createRole(role);
    return ResponseEntity.ok(role);
  }

  @GetMapping()
  public ResponseEntity<Role> getRoleByName(@RequestParam("name") String name) {
    Role role = roleService.getRoleByName(name);
    return ResponseEntity.ok(role);
  }

  @DeleteMapping()
  public ResponseEntity<String> deleteRoleByName(@RequestParam("name") String name) {
    roleService.deleteRoleByName(name);
    return ResponseEntity.ok("role delete successfully");
  }

  @GetMapping("/list")
  public ResponseEntity<List<Role>> getRoleList() {
    List<Role> employeeList = roleService.getRoleList();
    return ResponseEntity.ok(employeeList);
  }

  @PostMapping("/assign-role")
  public ResponseEntity<String> assignRole(@RequestBody EmployeeRole employeeRole) {
    roleService.assignRole(employeeRole.getUserId(), employeeRole.getRoleName());
    return ResponseEntity.ok("role mapping is successful");
  }
}
