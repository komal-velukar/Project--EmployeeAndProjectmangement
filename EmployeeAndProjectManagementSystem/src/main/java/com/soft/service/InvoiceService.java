

package com.soft.service;

import com.soft.model.Invoice;
import com.soft.repository.InvoiceRepository; // Assuming your repository is named InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Needed for the findById method

@Service
public class InvoiceService {
    
    // Inject the Invoice Repository
    @Autowired 
    private InvoiceRepository invoiceRepository; 

    // Assuming you have a submitInvoice method
    public Invoice submitInvoice(Invoice invoice) {
        // Implement submission logic here
        return invoiceRepository.save(invoice);
    }

    /**
     * Finds an Invoice by ID.
     * Guaranteed to return a plain Invoice object or throw an exception.
     * This makes the Controller's call compile correctly.
     */
    public Invoice findById(Long id) {
        // Use the repository's findById method (which returns Optional)
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        
        // Use orElseThrow() to unwrap the Optional.
        // If the invoice is not found, it throws an exception (which can be 
        // mapped to a 404 response by Spring's exception handling).
        return optionalInvoice.orElseThrow(() -> 
            new RuntimeException("Invoice not found with ID: " + id));
    }

    // Add other necessary InvoiceService methods (e.g., findAll)
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }
}