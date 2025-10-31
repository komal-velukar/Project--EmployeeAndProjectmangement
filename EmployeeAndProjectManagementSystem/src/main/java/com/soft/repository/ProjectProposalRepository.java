

package com.soft.repository;

import com.soft.model.ProjectProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectProposalRepository extends JpaRepository<ProjectProposal, Long> {
    // Standard CRUD and filtering by status are handled by findAll() and Stream().
}