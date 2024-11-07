package com.cosek.edms.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Component
public class PdfGenerator {

    public ByteArrayOutputStream generateGenericPdf(String title, Map<String, Object> dataMap) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font fontContent = new Font(Font.FontFamily.HELVETICA, 12);

        // Add title to the document
        Paragraph titleParagraph = new Paragraph(title, fontTitle);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph(" "));

        // Add data map entries to the document
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), fontContent));
        }

        document.close();
        return out;
    }

    public <T> ByteArrayOutputStream generateListPdf(String title, List<T> dataList, List<String> headers) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font fontContent = new Font(Font.FontFamily.HELVETICA, 12);

        // Add title to the document
        Paragraph titleParagraph = new Paragraph(title, fontTitle);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph(" "));

        // Add headers
        if (!headers.isEmpty()) {
            document.add(new Paragraph(String.join(" | ", headers), fontContent));
        }

        // Add each item in the data list
        for (T item : dataList) {
            document.add(new Paragraph(item.toString(), fontContent));
        }

        document.close();
        return out;
    }
}
