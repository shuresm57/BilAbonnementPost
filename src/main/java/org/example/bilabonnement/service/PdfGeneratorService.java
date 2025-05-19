package org.example.bilabonnement.service;

import com.lowagie.text.*;
import java.util.List;
import com.lowagie.text.pdf.PdfWriter;
import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.model.contracts.ConditionReport;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {


    //Genererer leje-kontrakt som PDF - ByteArrayInputStream er
    public ByteArrayInputStream generateRentalContractPdf(RentalContract contract) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            //Initialiserer PdfWriter og knytter til ByteArrayOutputStream
            PdfWriter.getInstance(document, out);
            //'Åbner' et dokument (del af iText library), som er en tom skabelon for PDF'er
            document.open();

            // Tilføjer og formaterer logo
            try {
                Image logo = Image.getInstance("src/main/resources/static/logo.png");
                logo.scaleToFit(100, 100);
                logo.setAlignment(Image.ALIGN_RIGHT);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Logo not found or failed to load.");
            }

            // Angiver Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font spacer = FontFactory.getFont(FontFactory.HELVETICA, 6);

            // Titel
            Paragraph title = new Paragraph("Lejeaftale", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ", spacer)); // spacing

            // Kontrakt-detaljer
            document.add(new Paragraph("Lejeperiode", subTitleFont));
            document.add(new Paragraph("Startdato: " + contract.getFromDate(), bodyFont));
            document.add(new Paragraph("Slutdato: " + contract.getToDate(), bodyFont));
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("Køretøj & Kunde", subTitleFont));
            document.add(new Paragraph("Bil: " + contract.getCarDescription(), bodyFont));
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

    public ByteArrayInputStream generateConditionReportPdf(ConditionReport report, List<Damage> damages) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font spacer = FontFactory.getFont(FontFactory.HELVETICA, 6);

            // Titel
            Paragraph title = new Paragraph("Tilstandsrapport", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ", spacer));

            // Rapportdetaljer
            document.add(new Paragraph("Rapport ID: " + report.getCondition_report_id(), bodyFont));
            document.add(new Paragraph("Kontrakt ID: " + report.getContract_id(), bodyFont));
            document.add(new Paragraph("Rapportdato: " + report.getReport_date(), bodyFont));
            document.add(new Paragraph("Returdato: " + report.getReturn_date(), bodyFont));
            document.add(new Paragraph("Kørte kilometer: " + report.getKm_travelled() + " km", bodyFont));
            document.add(new Paragraph("Samlet pris: " + report.getCost() + " DKK", bodyFont));
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("Skader", subTitleFont));
            document.add(new Paragraph(" ", spacer));

            for (Damage damage : damages) {
                document.add(new Paragraph("- " + damage.getDescription() + " (" + damage.getPrice() + " kr)", bodyFont));
                document.add(new Paragraph(" ", spacer));
            }

            document.add(new Paragraph("--------------------------------------------", bodyFont));
            document.add(new Paragraph("Tak for din rapportering - BilAbonnement A/S", bodyFont));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


}
