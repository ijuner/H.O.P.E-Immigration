package ca.conestoga.project.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


public class PdfGenerator {

    public static void main(String[] args) {
        //a();
    }
    private static final String DIRECTORY = "pdf/";

    public static String generate(String titleStr, List<String> content) {
        try {
            Path uploadPath = Paths.get(DIRECTORY);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = DIRECTORY + UUID.randomUUID() + ".pdf";

            PdfDocument pdf = new PdfDocument(new PdfWriter(fileName));

            Document document = new Document(pdf);
            Paragraph title = new Paragraph(titleStr)
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMargin(20);
            document.add(title);

            content.forEach(c -> document.add(new Paragraph(c).setFontSize(12)));
            document.close();

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
