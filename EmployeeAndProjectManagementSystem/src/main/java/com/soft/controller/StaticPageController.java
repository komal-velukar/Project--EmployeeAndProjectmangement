package com.soft.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {

    // 1. Controller for the root (/) to load the default page
    @GetMapping("/")
    public String loadDashboardRoot() {
        // Forward the request to the static dashboard file
        return "forward:/dashboard.html"; 
    }

    // 2. Controller for Employees (makes /employees work)
    @GetMapping("/employees")
    public String loadEmployees() {
        return "forward:/employees.html";
    }

    // 3. Controller for Proposals (makes /proposals work)
    @GetMapping("/proposals")
    public String loadProposals() {
        return "forward:/proposals.html";
    }

    // 4. Controller for Leave Requests (makes /leave-requests work)
    @GetMapping("/leave-request")
    public String loadLeaveRequests() {
        return "forward:/leave-request.html";
    }

    // 5. Controller for Login (makes /login work)
    @GetMapping("/login")
    public String loadLogin() {
        return "forward:/login.html";
    }
}