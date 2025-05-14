package org.example.bilabonnement.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
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

            // 1. Add logo
            try {
                Image logo = Image.getInstance("src/main/resources/static/logo.png");
                logo.scaleToFit(100, 100);
                logo.setAlignment(Image.ALIGN_RIGHT);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Logo not found or failed to load.");
            }

            // 2. Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font spacer = FontFactory.getFont(FontFactory.HELVETICA, 6);

            // 3. Title
            Paragraph title = new Paragraph("Lejeaftale", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ", spacer)); // spacing

            // 4. Contract Details
            document.add(new Paragraph("Lejeperiode", subTitleFont));
            document.add(new Paragraph("Startdato: " + contract.getFromDate(), bodyFont));
            document.add(new Paragraph("Slutdato: " + contract.getToDate(), bodyFont));
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("Køretøj & Kunde", subTitleFont));
            document.add(new Paragraph("Bil ID: " + contract.getCarId(), bodyFont));
            document.add(new Paragraph("Kunde: " + contract.getCustomerName(), bodyFont));
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("Aftalevilkår", subTitleFont));
            document.add(new Paragraph("Pris: " + contract.getPrice() + " DKK", bodyFont));
            document.add(new Paragraph("Max KM: " + contract.getMaxKm() + " km", bodyFont));
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("--------------------------------------------", bodyFont));
            document.add(new Paragraph("Tak for din reservation hos BilAbonnement A/S", bodyFont));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
