package com.example.ventes.facture.utils;

import com.example.coreapi.ventes.facture.FactureInfo;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class FacturePdfGenerator {
    public byte[] generatePdf(FactureInfo factureInfo) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Title
            document.add(new Paragraph("Facture")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(20)
                    .setMarginBottom(20));

            // Invoice details
            document.add(new Paragraph("Facture ID: " + factureInfo.getFactureId()));
            document.add(new Paragraph("Date: " + factureInfo.getDateFacture()));
            document.add(new Paragraph("User: " + factureInfo.getUser()));
            document.add(new Paragraph("Type: " + factureInfo.getTypeFacture()));
            document.add(new Paragraph("Montant: $" + factureInfo.getMontant()));

            // Products table
            document.add(new Paragraph("Produits").setBold().setMarginTop(20));
            Table table = new Table(1);
            table.addHeaderCell("Liste Des Produits");
            factureInfo.getProducts()
                    .forEach(product -> table.addCell(product.getProductId() + " : " + product.getQuantity()));
            document.add(table);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
