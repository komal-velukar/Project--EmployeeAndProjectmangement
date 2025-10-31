package com.soft.util; // Assuming this packag

import java.io.ByteArrayOutputStream;
// CRITICAL FIX 1: Use the standard Java Color class for lowagie iText
import java.awt.Color; 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections; 

import org.springframework.stereotype.Service;

// All iText imports use com.lowagie.text
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.soft.model.Invoice;
import com.soft.model.ProjectProposal;

@Service
public class InvoicePdfService {

    // CRITICAL FIX 2: Use Color instead of BaseColor in definitions
    private static final Font HEADING_FONT = new Font(0, 20, Font.BOLD, Color.DARK_GRAY); 
    private static final Font SUB_HEADING_FONT = new Font(0, 14, Font.BOLD, Color.BLACK);
    private static final Font DATA_FONT = new Font(0, 11);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    
    /**
     * Generates a PDF byte array for the given Invoice object.
     */
    public byte[] generateInvoicePdf(Invoice invoice) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            
            // ... (Invoice details paragraphs)

            // Table Header and Body: Use Color constants here too
            PdfPTable table = new PdfPTable(new float[]{4f, 1.5f}); 
            // ...
            
            // Table Header
            PdfPCell descHeader = new PdfPCell(new Phrase("Description", DATA_FONT));
            // CRITICAL FIX 3: Use Color.LIGHT_GRAY
            descHeader.setBackgroundColor(Color.LIGHT_GRAY); 
            table.addCell(descHeader);
            
            PdfPCell amountHeader = new PdfPCell(new Phrase("Amount", DATA_FONT));
            amountHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
            // CRITICAL FIX 4: Use Color.LIGHT_GRAY
            amountHeader.setBackgroundColor(Color.LIGHT_GRAY); 
            table.addCell(amountHeader);

            // ... (rest of the method)

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF for Invoice " + invoice.getInvoiceNumber() + ": " + e.getMessage(), e);
        }

        return out.toByteArray();
    }
    
    // ... (addParagraph and formatDate utility methods)
}