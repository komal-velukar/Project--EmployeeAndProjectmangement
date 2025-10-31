


package com.soft.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Requirement 10: Project Proposal Submission
     * Sends email to Project Manager and Finance Head.
     */
    public void sendProjectProposalNotification(String managerEmail, String financeEmail, String projectName) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        // Sends to both manager and finance head
        message.setTo(managerEmail); 
        message.setCc(financeEmail); 
        message.setSubject("New Project Proposal Submitted: " + projectName);
        
        // Content requirement (Note: The actual email text is quoted from the image)
        String content = "A new project proposal has been submitted. Please review and approve/reject it.";
        message.setText("Content: \"" + content + "\"");
        
        mailSender.send(message);
    }
    
    /**
     * Requirement 10: Invoice Generation
     * Sends email to the client when an invoice is generated.
     */
    public void sendInvoiceGenerationEmail(String clientEmail, String invoiceNumber, String projectName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(clientEmail); 
        message.setSubject("Invoice Generated: " + invoiceNumber);
        
        // Content requirement (Note: The actual email text is quoted from the image)
        String content = String.format(
            "Your invoice [%s] for the project [%s] has been generated. Please find the details attached.",
            invoiceNumber, projectName
        );
        
        message.setText("Content: \"" + content + "\"");
        
        // NOTE: For full implementation, this should use MimeMessageHelper to attach the PDF.
        mailSender.send(message);
    }

    /**
     * Requirement 10: Leave Request Submission
     * Sends email to the employee's reporting manager.
     */
    public void sendLeaveRequestSubmission(String reportingManagerEmail, String employeeName, String startDate, String endDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reportingManagerEmail);
        message.setSubject("New Leave Request from " + employeeName);
        
        // Content requirement (Note: The actual email text is quoted from the image)
        String content = String.format(
            "[%s] has submitted a leave request from [%s] to [%s]. Please review and approve/reject it.",
            employeeName, startDate, endDate
        );
        
        message.setText("Content: \"" + content + "\"");
        
        mailSender.send(message);
    }
}