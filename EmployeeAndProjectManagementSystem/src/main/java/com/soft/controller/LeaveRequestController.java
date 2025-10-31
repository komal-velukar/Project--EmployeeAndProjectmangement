


package com.soft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soft.model.LeaveRequest;
import com.soft.service.LeaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leave")
public class LeaveRequestController {
    
    @Autowired private LeaveService leaveService;
    // Assuming a repository or service exists to fetch ALL leaves for management view

    // Employee submits a request (Accessible to all authenticated users)
    @PostMapping("/submit")
    public LeaveRequest submitRequest(@Valid @RequestBody LeaveRequest request) {
        return leaveService.submitRequest(request);
    }

    // Manager/Admin can view all leave requests for approval
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/all")
    public List<LeaveRequest> getAllRequests() {
        // Return a list of all requests, typically filtered for PENDING status
        return leaveService.findAll(); // Assuming findAll() is implemented in LeaveService
    }
    
    // Manager/Admin can approve/reject
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/{id}/status")
    public LeaveRequest updateLeaveStatus(@PathVariable Long id, @RequestParam String status) {
        // Assuming a method exists in LeaveService to handle approval/rejection, 
        // including updating the employee's leave balance upon approval.
        return leaveService.updateStatus(id, status); 
    }
}