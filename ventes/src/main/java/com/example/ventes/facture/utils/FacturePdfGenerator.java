package com.example.ventes.facture.utils;

import com.example.coreapi.ventes.facture.FactureInfo;
import com.example.coreapi.ventes.payment.PurchasedProduct;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
            document.add(new Paragraph("Username: " + factureInfo.getUsername()));
            document.add(new Paragraph("Email: " + factureInfo.getEmail()));
            document.add(new Paragraph("Type: " + factureInfo.getTypeFacture()));
            document.add(new Paragraph("Montant Total: $" + factureInfo.getMontant()));
            document.add(new Paragraph("Frais de Livraison: $" + factureInfo.getDeliveryFees()));

            // Products table
            document.add(new Paragraph("Produits").setBold().setMarginTop(20));
            Table table = new Table(1);
            table.addHeaderCell("Liste Des Produits");

            List<PurchasedProduct> Productslist = new ArrayList<>(factureInfo.getProductsQuantites());

            for (int i = 0; i < Productslist.size(); i++) {
                table.addCell(factureInfo.getProductsNames().get(i) + "        :              "
                        + String.valueOf(Productslist.get(i).getQuantity()));
            }

            document.add(table);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
