

package com.soft.repository;

import com.soft.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Finds a Role by its name (e.g., "ROLE_ADMIN").
     * Required for mapping users to roles.
     */
    Optional<Role> findByName(String name);
}