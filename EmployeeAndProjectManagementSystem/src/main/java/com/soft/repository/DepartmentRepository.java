
package com.soft.repository;



import com.soft.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Remove the custom findOne(Long id) here. JpaRepository has findById(ID).
}