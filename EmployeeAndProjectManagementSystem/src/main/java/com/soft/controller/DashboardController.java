


package com.soft.controller;

import com.soft.repository.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
// Accessible to all authenticated users
public class DashboardController {

    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private DepartmentRepository departmentRepo;
    @Autowired private ProjectProposalRepository projectProposalRepo;

    /**
     * Requirement 7: Provides a summary of total counts for the dashboard KPIs.
     */
    @GetMapping("/summary")
    public Map<String, Long> getSummaryKPIs() {
        Map<String, Long> summary = new HashMap<>();
        
        summary.put("totalEmployees", employeeRepo.count());
        summary.put("totalDepartments", departmentRepo.count());
        summary.put("totalProposals", projectProposalRepo.count());
        // Add more counts as necessary (e.g., pending leaves, active projects)
        
        return summary;
    }

    /**
     * Requirement 7: Provides data for the "Employee distribution by department" chart.
     */
    @GetMapping("/employee-distribution")
    public List<Map<String, Object>> getEmployeeDistribution() {
        return employeeRepo.findAll().stream()
                .filter(e -> e.getDepartment() != null)
                .collect(Collectors.groupingBy(
                    e -> e.getDepartment().getName(), 
                    Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("department", entry.getKey());
                    data.put("count", entry.getValue());
                    return data;
                })
                .collect(Collectors.toList());
    }

    /**
     * Requirement 7: Provides data for the "Project proposals by status" chart.
     */
    @GetMapping("/proposal-status")
    public List<Map<String, Object>> getProposalStatus() {
        return projectProposalRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                    p -> p.getStatus(), 
                    Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("status", entry.getKey());
                    data.put("count", entry.getValue());
                    return data;
                })
                .collect(Collectors.toList());
    }
}