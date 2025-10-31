
package com.soft.service;



import com.soft.model.Employee;
import com.soft.model.LeaveRequest;
import com.soft.repository.EmployeeRepository;
import com.soft.repository.LeaveRequestRepository;
import com.soft.util.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService {
    @Autowired private LeaveRequestRepository leaveRepo;
    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private EmailService emailService; 

    public LeaveRequest submitRequest(LeaveRequest request) {
        
        // *CRITICAL FIX:* Correctly load the full Employee object using findById
        // The request object only contains a partial Employee (usually just the ID).
        Long employeeId = request.getEmployee().getId();
        
        // Use findById, which returns Optional, and throw if not present
        Employee employee = employeeRepo.findById(employeeId) 
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        // Set the fully managed employee object back on the request
        request.setEmployee(employee);

        // --- 1. Date Validation (Start before End) (Requirement 9) ---
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be before end date.");
        }
        
        // Calculate duration
        long requestedDays = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;
        request.setDurationDays((int) requestedDays);
        
        // --- 2. Leave Balance Validation (Requirement 9) ---
        if (employee.getLeaveBalance() < requestedDays) {
            throw new IllegalStateException("Insufficient leave balance. Requested: " + requestedDays 
                                            + ", Available: " + employee.getLeaveBalance() + " days.");
        }

        // Set initial status and save (Requirement 5: Confirmation Popup workflow starts)
        request.setStatus("Submitted");
        LeaveRequest savedRequest = leaveRepo.save(request);
        
        // --- 3. Email Notification (Requirement 10) ---
        if (employee.getReportingManager() != null) {
            emailService.sendLeaveRequestSubmission(
                employee.getReportingManager().getEmail(), 
                employee.getEmployeeName(), 
                request.getStartDate().toString(),
                request.getEndDate().toString()
            );
        }

        return savedRequest;
    }

    // Implementation for the Controller's GET /api/leave/all
    public List<LeaveRequest> findAll() {
        return leaveRepo.findAll();
    }

    // Implementation for the Controller's PUT /api/leave/{id}/status
    public LeaveRequest updateStatus(Long id, String status) {
        LeaveRequest existingRequest = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with ID: " + id));

        // Simple validation for status change
        if (status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Rejected")) {
            existingRequest.setStatus(status);
            
            // If approved, update the employee's leave balance
            if (status.equalsIgnoreCase("Approved")) {
                Employee employee = existingRequest.getEmployee();
                int duration = existingRequest.getDurationDays();
                
                // Deduct the days from the balance
                employee.setLeaveBalance(employee.getLeaveBalance() - duration);
                employeeRepo.save(employee);
                
                // Send approval email (or use a dedicated method in EmailService)
                // emailService.sendLeaveApproval(employee.getEmail(), ...);
            }
            
            return leaveRepo.save(existingRequest);
        } else {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}