
package com.soft.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username; // The login identifier (e.g., employee email)
    
    private String password; // The hashed password

    // Link to the core Employee record for profile data
    @OneToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "employee_id")
    private Employee employee; 
    
    // Many-to-Many relationship for roles (EAGER fetch is common for security)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
}
