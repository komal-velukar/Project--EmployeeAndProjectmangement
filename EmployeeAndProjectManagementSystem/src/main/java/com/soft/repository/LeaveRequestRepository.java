package com.soft.repository;

import com.soft.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    // Custom method to fetch pending requests for the manager dashboard, if needed.
    // List<LeaveRequest> findByStatus(String status);
}