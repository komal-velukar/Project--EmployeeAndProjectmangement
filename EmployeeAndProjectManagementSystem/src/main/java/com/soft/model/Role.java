
package com.soft.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // The name of the role (e.g., "ROLE_ADMIN", "ROLE_MANAGER")
    @Column(unique = true, nullable = false)
    private String name; 
    
    public Role(String name) {
        this.name = name;
    }
}