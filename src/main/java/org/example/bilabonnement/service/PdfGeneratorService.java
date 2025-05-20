package org.example.bilabonnement.service;

import com.lowagie.text.*;

import java.awt.*;
import java.io.*;
import java.util.List;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.model.contracts.ConditionReport;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.stereotype.Service;

@Service
public class PdfGeneratorService {


    //Genererer leje-kontrakt som PDF - ByteArrayInputStream er
    public ByteArrayInputStream generateRentalContractPdf(RentalContract contract) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Tilføj logo
            try {
                Image logo = Image.getInstance("src/main/resources/static/logo.png");
                logo.scaleToFit(100, 100);
                logo.setAlignment(Image.ALIGN_RIGHT);
                document.add(logo);
            } catch (Exception e) {
                System.err.println("Logo ikke fundet: " + e.getMessage());
            }

            // Definer skrifttyper
            Font titleFont    = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font bodyFont     = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font spacer       = FontFactory.getFont(FontFactory.HELVETICA, 6);
            Font linkFont     = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE, Color.BLUE);

            // Side 1: Kontrakt
            Paragraph title = new Paragraph("Lejeaftale", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("Lejeperiode", subTitleFont));
            document.add(new Paragraph("Startdato: " + contract.getFromDate(), bodyFont));
            document.add(new Paragraph("Slutdato:  " + contract.getToDate(), bodyFont));
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("Køretøj & Kunde", subTitleFont));
            document.add(new Paragraph("Bil:    " + contract.getCarDescription(), bodyFont));
            document.add(new Paragraph("Kunde:  " + contract.getCustomerName(), bodyFont));
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("Aftalevilkår", subTitleFont));
            document.add(new Paragraph("Pris:    " + contract.getPrice() + " DKK", bodyFont));
            document.add(new Paragraph("Max KM:  " + contract.getMaxKm()  + " km", bodyFont));

            Anchor externalLink = new Anchor("Prisoversigt", linkFont);
            externalLink.setReference("https://bilabonnement.dk/faq/liste-over-priser");
            document.add(externalLink);
            document.add(new Paragraph(" ", spacer));

            document.add(new Paragraph("--------------------------------------------", bodyFont));
            document.add(new Paragraph("Tak for din reservation hos BilAbonnement A/S", bodyFont));

            document.newPage();
            Paragraph priceTitle = new Paragraph("Liste over priser", titleFont);
            priceTitle.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(priceTitle);
            document.add(new Paragraph(" ", spacer));

            // Side 2: Liste over priser fra resource-fil
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            // Headers
            table.addCell(new PdfPCell(new Phrase("Fakturatekst", subTitleFont)));
            table.addCell(new PdfPCell(new Phrase("Størrelse på opkrævning", subTitleFont)));
            table.addCell(new PdfPCell(new Phrase("Hvorfor kan opkrævningen forekomme?", subTitleFont)));

            // Indlæs prislisten fra classpath (price-list.txt)
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("price-list.txt");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\t");
                    // Hvis linjen ikke har 3 dele, spring over
                    if (parts.length < 3) continue;
                    table.addCell(new PdfPCell(new Phrase(parts[0], bodyFont)));
                    table.addCell(new PdfPCell(new Phrase(parts[1], bodyFont)));
                    table.addCell(new PdfPCell(new Phrase(parts[2], bodyFont)));
                }
                document.add(table);
            } catch (IOException | NullPointerException e) {
                document.add(new Paragraph("Kunne ikke indlæse prislisten.", bodyFont));
            }

            document.close();
        } catch (Exception e) {
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
            document.add(new Paragraph("Kørte kilometer: " + report.getOdometer() + " km", bodyFont));
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
