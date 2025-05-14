package org.example.bilabonnement.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public ByteArrayInputStream generateRentalContractPdf(RentalContract contract) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Lejeaftale", titleFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Startdato: " + contract.getFromDate(), bodyFont));
            document.add(new Paragraph("Slutdato: " + contract.getToDate(), bodyFont));
            document.add(new Paragraph("Pris: " + contract.getPrice() + " DKK", bodyFont));
            document.add(new Paragraph("Max KM: " + contract.getMaxKm() + " km", bodyFont));
            document.add(new Paragraph("Kunde ID: " + contract.getCustomerId(), bodyFont));
            document.add(new Paragraph("Bil ID: " + contract.getCarId(), bodyFont));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
