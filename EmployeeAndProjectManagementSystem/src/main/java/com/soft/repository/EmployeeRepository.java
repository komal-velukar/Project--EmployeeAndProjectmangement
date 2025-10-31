
package com.soft.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import com.soft.model.Employee;


/**
 * Repository interface for Employee entity.
 * Provides standard CRUD operations and custom finder methods.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Finds an Employee by their email address.
     * Used primarily by EmployeeUserDetailsService for Spring Security authentication.
     * * @param email The email address to search for.
     * @return The Employee object, or null if not found.
     */
    Employee findByEmail(String email);

    /**
     * Finds an Employee by their custom generated employee ID (e.g., "ER0001").
     * Used for internal logic like checking ID existence or generation.
     * * @param employeeId The custom employee ID to search for.
     * @return The Employee object, or null if not found.
     */
    Optional<Employee> findByEmployeeId(String employeeId);
    
    // NOTE: All standard methods like save(), findById(), findAll(), deleteById() 
    // are inherited from JpaRepository.
}