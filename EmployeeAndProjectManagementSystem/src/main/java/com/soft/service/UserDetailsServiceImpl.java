package com.soft.service;

 // Adjust package name as necessary

import com.soft.model.Employee; // Ensure this points to your Employee entity
import com.soft.repository.EmployeeRepository; // Ensure this points to your Repository

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional; // <--- MAKE SURE THIS IMPORT IS PRESENT

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
        
        // --- FIX Applied here ---
        // The orElseThrow method requires a Supplier (a lambda function) that returns the exception.
        // The syntax is: .orElseThrow(() -> new Exception("Message"))
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);
        
        Employee employee = employeeOptional.orElseThrow(
            // Use the standard lambda function syntax for orElseThrow
            () -> new UsernameNotFoundException("Employee not found with ID: " + employeeId)
        );

        // --- Important Conversion to Spring Security's UserDetails ---
        
        // Assuming your Employee entity has getRole() returning a String like "ADMIN"
        String role = employee.getRole().toUpperCase(); 
        
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + role) 
        );

        return new User(
                employee.getEmployeeId(), // Used as the username/principal
                employee.getPassword(),   // The hashed password from the database
                authorities               // The roles/authorities
        );
    }
}