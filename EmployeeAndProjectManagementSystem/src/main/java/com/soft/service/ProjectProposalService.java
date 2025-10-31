
package com.soft.service;



import com.soft.model.ProjectProposal;
import com.soft.repository.ProjectProposalRepository;
import com.soft.util.EmailService;
// Note: jakarta.validation.Valid is typically used on controller arguments, not service args
// import jakarta.validation.Valid; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectProposalService {
    @Autowired private ProjectProposalRepository proposalRepo;
    @Autowired private EmailService emailService;

    // CREATE / SUBMIT (Used by Controller)
    public ProjectProposal submitProposal(ProjectProposal proposal) {
        
        // Team Member Subform Validation (Requirement 9) - Now uses the correct getter
        if (proposal.getTeamMembers() == null || proposal.getTeamMembers().isEmpty()) {
            throw new IllegalArgumentException("Project team members list cannot be empty.");
        }
        
        proposal.setStatus("Submitted");
        ProjectProposal savedProposal = proposalRepo.save(proposal);

        // Email Notification to Manager/Finance (Requirement 10)
        String projectManagerEmail = savedProposal.getProjectManager().getEmail();
        String financeHeadEmail = "finance.head@soft.com"; // Placeholder
        
        emailService.sendProjectProposalNotification(
            projectManagerEmail, 
            financeHeadEmail, 
            savedProposal.getProjectName()
        );

        return savedProposal;
    }
    
    // READ ALL
    public List<ProjectProposal> findAll() {
        return proposalRepo.findAll();
    }

    // READ BY ID - *CRITICAL FIX APPLIED*
    public ProjectProposal findById(Long id) {
        // Use findById().orElseThrow()
        return proposalRepo.findById(id)
                           .orElseThrow(() -> new RuntimeException("Project Proposal not found with id: " + id));
    }

    /**
     * Workflow Action: Approve or Reject a proposal (Requirement 4)
     */
    public ProjectProposal updateStatus(Long id, String newStatus) {
        ProjectProposal existingProposal = findById(id);
        
        if (!newStatus.equals("Approved") && !newStatus.equals("Rejected") && !newStatus.equals("Under Review")) {
             throw new IllegalArgumentException("Invalid status provided. Must be Approved, Rejected, or Under Review.");
        }
        
        existingProposal.setStatus(newStatus);
        
        // You might add an email notification here for the status change
        
        return proposalRepo.save(existingProposal);
    }

    // UPDATE DETAILS (Implemented for ProjectController)
	public ProjectProposal updateProposalDetails(Long id, ProjectProposal updatedProposal) {
        ProjectProposal existingProposal = findById(id);

        // Update fields that can be changed (excluding ID and status)
        existingProposal.setProjectName(updatedProposal.getProjectName());
        existingProposal.setBudget(updatedProposal.getBudget());
        existingProposal.setProjectManager(updatedProposal.getProjectManager());
        existingProposal.setTeamMembers(updatedProposal.getTeamMembers());
        
        return proposalRepo.save(existingProposal);
	}

    // DELETE (Implemented for ProjectController)
	public void deleteProposal(Long id) {
		proposalRepo.deleteById(id);
	}
}