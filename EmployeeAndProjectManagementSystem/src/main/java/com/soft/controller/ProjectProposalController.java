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

import com.soft.model.ProjectProposal;
import com.soft.service.ProjectProposalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/proposals")
public class ProjectProposalController {
    
    @Autowired private ProjectProposalService proposalService;

    // Any authenticated user (Employee, Manager, Admin) can submit a proposal
    @PostMapping
    public ProjectProposal submitProposal(@Valid @RequestBody ProjectProposal proposal) {
        return proposalService.submitProposal(proposal);
    }

    // Manager/Admin can view all proposals
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping
    public List<ProjectProposal> getAllProposals() {
        return proposalService.findAll();
    }
    
    // Manager/Admin can approve/reject a proposal (Requirement 4)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/{id}/status")
    public ProjectProposal updateProposalStatus(@PathVariable Long id, @RequestParam String status) {
        return proposalService.updateStatus(id, status);
    }
}

