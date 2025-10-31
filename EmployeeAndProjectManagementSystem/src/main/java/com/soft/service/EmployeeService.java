
package com.soft.service;








import com.soft.model.Employee;
import com.soft.model.User; 
import com.soft.model.Role; 
import com.soft.repository.EmployeeRepository;
import com.soft.repository.UserRepository;
import com.soft.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional
import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; 

    // Constructor Injection: Preferred method for dependencies
    @Autowired
    public EmployeeService(
        EmployeeRepository employeeRepository,
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder) { 
        
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new Employee record and creates an associated User login account.
     * This method must be @Transactional to ensure both saves succeed or both fail.
     */
    @Transactional
    public Employee saveNewEmployee(Employee employee, String username, String rawPassword) {
        
        // 1. Save the core Employee record first to get its generated ID
        Employee savedEmployee = employeeRepository.save(employee);
        
        // 2. Create the associated User login record
        User user = new User();
        
        // FIX CHECK: Ensure your 'User' entity has a public method 'setUsername(String)'
        user.setUsername(username); 
        
        // FIX CHECK: Ensure your 'User' entity has a public method 'setPassword(String)'
        user.setPassword(passwordEncoder.encode(rawPassword)); 
        
        // FIX CHECK: Ensure your 'User' entity has a public method 'setEmployee(Employee)'
        // This is necessary because User is the owning side of the OneToOne relationship (it holds the FK)
        user.setEmployee(savedEmployee);
        
        // Assign a default role
        Role defaultRole = roleRepository.findByName("ROLE_EMPLOYEE")
                                         .orElseThrow(() -> new RuntimeException("Default role 'ROLE_EMPLOYEE' not found. Ensure roles are initialized in the database."));
        
        // FIX CHECK: Ensure your 'User' entity has a public method 'setRoles(Set<Role>)'
        user.setRoles(Set.of(defaultRole));
        
        // 3. Save the User record, which also sets the foreign key to the Employee
        userRepository.save(user); 
        
        return savedEmployee;
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
}