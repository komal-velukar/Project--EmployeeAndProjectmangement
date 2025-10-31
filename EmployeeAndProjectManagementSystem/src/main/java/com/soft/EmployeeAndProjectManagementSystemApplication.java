package com.soft;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration; // <-- REQUIRED IMPORT

// ADDITION 1: Exclude Spring Batch Autoconfiguration
@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
public class EmployeeAndProjectManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeAndProjectManagementSystemApplication.class, args);
    }
}