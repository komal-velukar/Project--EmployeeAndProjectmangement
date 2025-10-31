


package com.soft.service;


import com.soft.model.Department;
import com.soft.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    
    @Autowired private DepartmentRepository departmentRepo;

    public Department createDepartment(Department department) {
        return departmentRepo.save(department);
    }
    
    public List<Department> findAll() {
        return departmentRepo.findAll();
    }
    
    // *** FIX APPLIED HERE: Using findById().orElseThrow() ***
    public Department findById(Long id) {
        return departmentRepo.findById(id)
                             .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existingDepartment = findById(id);
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setLocation(updatedDepartment.getLocation());
        // Assuming DepartmentHead is a simple field or a simple model that has been set
        existingDepartment.setDepartmentHead(updatedDepartment.getDepartmentHead()); 
        return departmentRepo.save(existingDepartment);
    }

    public void deleteDepartment(Long id) {
        departmentRepo.deleteById(id);
    }
}