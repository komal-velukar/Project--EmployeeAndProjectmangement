package com.soft.model;




import java.io.Serializable;
import java.time.LocalDate;

// *** CRITICAL FIX: Changed capitalized Jakarta to lowercase jakarta ***
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
// Note: jakarta.validation is only needed if you are using Spring Boot 3+

// *** CRITICAL FIX: Changed capitalized Lombok to lowercase lombok ***
import lombok.Data;
// If you are using Spring Boot 2.x, replace all 'jakarta' imports with 'javax'.

@Entity
@Data
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String employeeId; 
    
    @NotBlank(message = "Employee Name is required")
    private String employeeName; 
    
    @Email(message = "Email must be a valid format") 
    @Column(unique = true, nullable = false)
    private String email; 
    
    @jakarta.validation.constraints.Pattern(regexp="^[0-9]+$", message = "Contact Number must contain only numeric values") 
    private String contactNumber;
    
    @PastOrPresent(message = "Joining date cannot be a future date") 
    private LocalDate joiningDate;
    
    @ManyToOne 
    @JoinColumn(name = "dept_id")
    private Department department; 

    @ManyToOne 
    @JoinColumn(name = "manager_id")
    private Employee reportingManager; 

    private int leaveBalance = 20; 
    private String role; 
    private String password;
	public Long getId() {
		return id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public String getEmail() {
		return email;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public LocalDate getJoiningDate() {
		return joiningDate;
	}
	public Department getDepartment() {
		return department;
	}
	public Employee getReportingManager() {
		return reportingManager;
	}
	public int getLeaveBalance() {
		return leaveBalance;
	}
	public String getRole() {
		return role;
	}
	public String getPassword() {
		return password;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public void setReportingManager(Employee reportingManager) {
		this.reportingManager = reportingManager;
	}
	public void setLeaveBalance(int leaveBalance) {
		this.leaveBalance = leaveBalance;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeId=" + employeeId + ", employeeName=" + employeeName + ", email="
				+ email + ", contactNumber=" + contactNumber + ", joiningDate=" + joiningDate + ", department="
				+ department + ", reportingManager=" + reportingManager + ", leaveBalance=" + leaveBalance + ", role="
				+ role + ", password=" + password + "]";
	}

    // NOTE: Because you included the getters/setters/toString() manually, 
    // the @Data annotation is redundant, but leaving it here for completeness.
    // The manual methods you provided are correct and complete.
    // ... (Your manual getters/setters/toString remain here) ...
}