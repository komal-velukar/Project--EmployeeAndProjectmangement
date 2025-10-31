

package com.soft.controller;

import com.soft.model.Invoice;
import com.soft.service.InvoiceService;
import com.soft.util.InvoicePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException; // <-- Needed for the exception

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired private InvoiceService invoiceService;
    @Autowired private InvoicePdfService invoicePdfService; 

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.submitInvoice(invoice);
    }

    // PDF Download Endpoint (Requirement 5)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/{id}/pdf")
    public ResponseEntity<ByteArrayResource> downloadInvoicePdf(@PathVariable Long id) {
        
        // --- CRITICAL FIX: Direct assumption that Service returns Invoice is often wrong. ---
        // We must assume the service returns an Optional OR handle the not-found case here.
        // The most robust solution is to let the Service handle the Optional conversion 
        // using orElseThrow, but if the Service is failing, we handle it defensively here:
        
        Invoice invoice;
        try {
             // Assuming the service uses the orElseThrow pattern and returns the Invoice
             invoice = invoiceService.findById(id); 
        } catch (NoSuchElementException e) {
             // If the service's findById uses .get() or .orElseThrow() that is not handling 
             // the exception gracefully, we can just throw a standard Spring exception:
             throw new RuntimeException("Invoice not found with ID: " + id);
        } catch (RuntimeException e) {
             // If the service throws a custom RuntimeException (like the one we suggested previously)
             throw e;
        }

        byte[] pdfBytes = invoicePdfService.generateInvoicePdf(invoice); 
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Invoice_" + invoice.getInvoiceNumber() + ".pdf");
        
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}