
package com.soft.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Requirement 9: Auto-generated INV001, INV002
    @Column(unique = true, nullable = false)
    private String invoiceNumber; 
    
    @NotBlank
    private String clientName;
    
    @NotNull
    @Min(value = 0, message = "Amount must be a positive number") // Requirement 9: Validation
    private Double amount;
    
    @PastOrPresent
    private LocalDate issueDate;

    // Requirement 9: Due Date Validation
    @FutureOrPresent(message = "Due Date cannot be a past date") 
    private LocalDate dueDate;
    
    // Lookup from Project Proposal (Requirement 1)
    @ManyToOne 
    @JoinColumn(name = "project_proposal_id")
    private ProjectProposal project;

	public Long getId() {
		return id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getClientName() {
		return clientName;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public ProjectProposal getProject() {
		return project;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public void setProject(ProjectProposal project) {
		this.project = project;
	}

}