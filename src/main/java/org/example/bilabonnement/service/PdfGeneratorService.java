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

    private static final Font TITLE_FONT    = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
    private static final Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    private static final Font BODY_FONT     = FontFactory.getFont(FontFactory.HELVETICA, 12);
    private static final Font LINK_FONT     = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE, Color.BLUE);

    public ByteArrayInputStream generateRentalContractPdf(RentalContract contract) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            addLogo(document);

            addCentered(document, "Lejeaftale", TITLE_FONT);
            addEmptyLines(document, 1);

            addSubSection(document, "Lejeperiode",
                    "Startdato: " + contract.getFromDate(),
                    "Slutdato:  " + contract.getToDate());

            addSubSection(document, "Køretøj & Kunde",
                    "Bil:    " + contract.getCarDescription(),
                    "Kunde:  " + contract.getCustomerName());

            addSubSection(document, "Aftalevilkår",
                    "Pris:    " + contract.getPrice() + " DKK",
                    "Max KM:  " + contract.getMaxKm() + " km");

            Anchor link = new Anchor("Prisoversigt", LINK_FONT);
            link.setReference("https://bilabonnement.dk/faq/liste-over-priser");
            document.add(link);

            document.add(new Paragraph("--------------------------------------------", BODY_FONT));
            document.add(new Paragraph("Tak for din reservation hos BilAbonnement A/S", BODY_FONT));

            document.newPage();
            addCentered(document, "Liste over priser", TITLE_FONT);
            addEmptyLines(document, 1);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            addTableHeader(table, "Fakturatekst", "Størrelse på opkrævning", "Hvorfor kan opkrævningen forekomme?");
            loadPriceList(table);
            document.add(table);

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

            addCentered(document, "Tilstandsrapport", TITLE_FONT);
            addEmptyLines(document, 1);

            addParagraph(document, "Rapport ID: " + report.getCondition_report_id());
            addParagraph(document, "Kontrakt ID: " + report.getContract_id());
            addParagraph(document, "Rapportdato: " + report.getReport_date());
            addParagraph(document, "Returdato: " + report.getReturn_date());
            addParagraph(document, "Kørte kilometer: " + report.getOdometer() + " km");
            addParagraph(document, "Samlet pris: " + report.getCost() + " DKK");
            addParagraph(document, "Kunde: " + report.getCustomerName());
            addParagraph(document, "Email: " + report.getEmail());
            addParagraph(document, "Bil: " + report.getBrand() + " " + report.getModel() + " - " + report.getRegNo());
            addEmptyLines(document, 1);

            addSubTitle(document, "Skader");
            addEmptyLines(document, 1);
            for (Damage d : damages) {
                addParagraph(document, "- " + d.getDescription() + " (" + d.getPrice() + " kr)");
            }

            document.add(new Paragraph("--------------------------------------------", BODY_FONT));
            document.add(new Paragraph("Tak for din rapportering - BilAbonnement A/S", BODY_FONT));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }


    private void addLogo(Document doc) {
        try {
            Image logo = Image.getInstance("src/main/resources/static/logo.png");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Image.ALIGN_RIGHT);
            doc.add(logo);
        } catch (Exception ignored) {}
    }

    private void addCentered(Document doc, String text, Font font) throws DocumentException {
        Paragraph p = new Paragraph(text, font);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
    }

    private void addSubSection(Document doc, String title, String... lines) throws DocumentException {
        addSubTitle(doc, title);
        for (String line : lines) addParagraph(doc, line);
        addEmptyLines(doc, 1);
    }

    private void addSubTitle(Document doc, String text) throws DocumentException {
        doc.add(new Paragraph(text, SUBTITLE_FONT));
    }

    private void addParagraph(Document doc, String text) throws DocumentException {
        doc.add(new Paragraph(text, BODY_FONT));
    }

    private void addEmptyLines(Document doc, int count) throws DocumentException {
        for (int i = 0; i < count; i++) doc.add(new Paragraph(" ", BODY_FONT));
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        for (String h : headers) table.addCell(new PdfPCell(new Phrase(h, SUBTITLE_FONT)));
    }

    private void loadPriceList(PdfPTable table) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("price-list.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    table.addCell(new PdfPCell(new Phrase(parts[0], BODY_FONT)));
                    table.addCell(new PdfPCell(new Phrase(parts[1], BODY_FONT)));
                    table.addCell(new PdfPCell(new Phrase(parts[2], BODY_FONT)));
                }
            }
        } catch (Exception e) {
        }
    }


}
