
package com.soft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.model.ProjectProposal;
import com.soft.service.ProjectProposalService;

import jakarta.validation.Valid;

/**
 * Controller for CRUD operations on Project Proposals.
 * Note: Submission and Status Update are already in ProjectProposalController.
 * This handles the rest of the management tasks.
 */
@RestController
@RequestMapping("/api/projects")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')") // Admin/Manager can manage projects
public class ProjectController {
    
    @Autowired private ProjectProposalService proposalService;

    // READ ALL (used for general project list view)
    @GetMapping
    public List<ProjectProposal> getAllProjects() {
        return proposalService.findAll();
    }
    
    // READ BY ID
    @GetMapping("/{id}")
    public ProjectProposal getProjectById(@PathVariable Long id) {
        return proposalService.findById(id);
    }
    
    // UPDATE
    @PutMapping("/{id}")
    public ProjectProposal updateProject(@PathVariable Long id, @Valid @RequestBody ProjectProposal updatedProposal) {
        // Assuming a dedicated update method in the service to handle non-status field updates
        return proposalService.updateProposalDetails(id, updatedProposal); 
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // Only ADMIN should delete core data
    public void deleteProject(@PathVariable Long id) {
        proposalService.deleteProposal(id); // Assuming dedicated delete method in service
    }
}