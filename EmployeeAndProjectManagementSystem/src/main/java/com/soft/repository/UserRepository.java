

package com.soft.repository;

import com.soft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Required method for Spring Security to find a user by their login name.
     * @param username The login username (e.g., "admin@corp.com")
     * @return An Optional containing the User if found.
     */
    Optional<User> findByUsername(String username);
}