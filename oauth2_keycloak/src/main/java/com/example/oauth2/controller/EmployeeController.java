package com.example.oauth2.controller;

import com.example.oauth2.dto.Employee;
import com.example.oauth2.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/employee", produces = {"application/json"})
@AllArgsConstructor
public class EmployeeController {
  private final EmployeeService employeeService;

  @PostMapping("/save")
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
    employee = employeeService.createEmployee(employee);
    return ResponseEntity.ok(employee);
  }

  @PutMapping("/update-profile")
  public ResponseEntity<Employee> updateEmployeeProfile(@RequestBody Employee employee) {
    employee = employeeService.updateEmployeeProfile(employee);
    return ResponseEntity.ok(employee);
  }

  @PutMapping("/{employeeId}/send-verify-email")
  public ResponseEntity<String> sendVerificationEmail(@PathVariable String employeeId) {
    employeeService.emailVerification(employeeId);
    return ResponseEntity.ok("Send verification email successfully");
  }

  @PutMapping("/update-password")
  public ResponseEntity<String> updateEmployeePassword(Principal principal) {
    employeeService.updateEmployeePassword(principal.getName());
    return ResponseEntity.ok("Password updated successfully");
  }

  @PutMapping("/forgot-password")
  public ResponseEntity<String> forgotEmployeePassword(@RequestParam String username) {
    employeeService.resetEmployeePassword(username);
    return ResponseEntity.ok("Reset password successfully");
  }

  @GetMapping("/byId")
  public ResponseEntity<Employee> getEmployeeById(@RequestParam("id") String id) {
    Employee employee = employeeService.getEmployeeById(id);
    return ResponseEntity.ok(employee);
  }

  @GetMapping("/byUsername")
  public ResponseEntity<Employee> getEmployeeByUsername(@RequestParam("username") String username) {
    Employee employee = employeeService.getEmployeeByUsername(username);
    return ResponseEntity.ok(employee);
  }

  @DeleteMapping()
  public ResponseEntity<String> deleteEmployee(@RequestParam("id") String id) {
    employeeService.deleteEmployeeById(id);
    return ResponseEntity.ok("employee delete successfully");
  }

  @GetMapping("/list")
  public ResponseEntity<List<Employee>> getEmployeeList() {
    List<Employee> employeeList = employeeService.getEmployeeList();
    return ResponseEntity.ok(employeeList);
  }
}
