package com.soft.model;



import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Entity
@Data
public class ProjectProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String projectName;
    
    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private Employee projectManager; 
    
    @NotNull
    @Min(value = 0, message = "Budget must be a positive number") 
    private Double budget;
    
    @PastOrPresent(message = "Submission Date cannot be a future date") 
    private LocalDate submissionDate;
    
    @ElementCollection 
    @CollectionTable(name = "proposal_team_member", joinColumns = @JoinColumn(name = "proposal_id"))
    private List<TeamMember> teamMembers; 
    
    private String status = "Submitted"; 
    
    @Embeddable
    @Data
    public static class TeamMember {
        private String employeeName;
        private String role; 
    }

    // *** CRITICAL FIX: The return type must be List<TeamMember> ***
	public List<TeamMember> getTeamMembers() {
		return teamMembers; 
	}
    // ... (All other manual getters/setters remain the same) ...
    // ... (Skipping verbose getters/setters for brevity here) ...

	public Long getId() {return id;}
	public String getProjectName() {return projectName;}
	public Employee getProjectManager() {return projectManager;}
	public Double getBudget() {return budget;}
	public LocalDate getSubmissionDate() {return submissionDate;}
	public String getStatus() {return status;}
	public void setId(Long id) {this.id = id;}
	public void setProjectName(String projectName) {this.projectName = projectName;}
	public void setProjectManager(Employee projectManager) {this.projectManager = projectManager;}
	public void setBudget(Double budget) {this.budget = budget;}
	public void setSubmissionDate(LocalDate submissionDate) {this.submissionDate = submissionDate;}
	public void setTeamMembers(List<TeamMember> teamMembers) {this.teamMembers = teamMembers;}
	public void setStatus(String status) {this.status = status;}
	
	@Override
	public String toString() {
		return "ProjectProposal [id=" + id + ", projectName=" + projectName + ", projectManager=" + projectManager
				+ ", budget=" + budget + ", submissionDate=" + submissionDate + ", teamMembers=" + teamMembers
				+ ", status=" + status + "]";
	} 
}