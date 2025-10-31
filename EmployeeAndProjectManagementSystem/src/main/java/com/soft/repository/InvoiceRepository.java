

package com.soft.repository;

import com.soft.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Standard CRUD inherited. Invoice generation logic is in the Service layer.
}