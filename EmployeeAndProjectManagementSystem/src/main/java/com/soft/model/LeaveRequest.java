package com.soft.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; 
    
    @NotBlank
    private String leaveType; // Requirement 9: Validation
    
    @NotNull
    private LocalDate startDate;
    
    @NotNull
    private LocalDate endDate;
    
    @NotBlank
    private String reason;
    
    private String status = "Submitted"; // New status field
    
    // Utility field for validation/workflow
    @Transient 
    private int durationDays;

	public Long getId() {
		return id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getReason() {
		return reason;
	}

	public String getStatus() {
		return status;
	}

	public int getDurationDays() {
		return durationDays;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDurationDays(int durationDays) {
		this.durationDays = durationDays;
	} 
}
