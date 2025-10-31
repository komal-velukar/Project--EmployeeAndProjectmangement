

package com.soft.controller;





import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.model.Employee;
import com.soft.service.EmployeeService;
// CustomEmployeeDetails is usually not directly used here but is good practice to keep the import
 

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    @Autowired private EmployeeService employeeService;

    // Admin can create/edit all records
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody EmployeeRegistrationRequest request) {
        
        // 1. Validate if the Employee data exists in the request
        if (request.getEmployee() == null) {
            // Or throw a more specific exception
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }

        Employee employee = employeeService.saveNewEmployee(
            request.getEmployee(), 
            request.getUsername(), 
            request.getPassword()
        );
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    // Admin/Manager can view all records
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')") 
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }
    
    /**
     * Employee can view their own record.
     * Admin/Manager can view all records.
     * * NOTE: The expression relies on CustomEmployeeDetails having a method 
     * 'getEmployeeId()' that returns a Long (or 'employeeId' field).
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER') or #id.toString() == authentication.principal.employeeId.toString()")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findEmployeeById(id)
                                          .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ResponseEntity.ok(employee);
    }
    
    /**
     * Corrected Helper class for combined registration request.
     * Needs proper Lombok annotations or explicit constructors/getters/setters 
     * to be properly bound by Spring during deserialization.
     */
    public static class EmployeeRegistrationRequest {
        @Valid // Ensure the nested Employee object is also validated
        private Employee employee;
        private String username;
        private String password;
        
        // Added explicit setters for Spring to bind JSON to fields
        public void setEmployee(Employee employee) { this.employee = employee; }
        public void setUsername(String username) { this.username = username; }
        public void setPassword(String password) { this.password = password; }

        // Getters (as already defined)
        public Employee getEmployee() { return employee; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
    }
}