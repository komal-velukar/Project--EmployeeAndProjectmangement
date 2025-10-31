
package com.soft.service;

import com.soft.model.Employee;
import com.soft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email);

        if (employee == null) {
            throw new UsernameNotFoundException("Employee not found with email: " + email);
        }

        // Role-Based Authorization
        GrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole()); // ADMIN, MANAGER, EMPLOYEE (Requirement 3)
        
        return new org.springframework.security.core.userdetails.User(
            employee.getEmail(),
            employee.getPassword(), 
            Collections.singleton(authority)
        );
    }
}