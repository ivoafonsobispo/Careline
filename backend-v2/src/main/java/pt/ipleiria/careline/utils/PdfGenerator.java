package pt.ipleiria.careline.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfGenerator {
    public byte[] generateDiagnosisPDF(DiagnosisEntity diagnosisEntity) throws DocumentException, IOException {
        // Create document with A4 size
        Document document = new Document(PageSize.A4);

        // ByteArrayOutputStream for storing the generated PDF
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (byteArrayOutputStream) {
            // Create PdfWriter instance with the ByteArrayOutputStream
            PdfWriter.getInstance(document, byteArrayOutputStream);

            // Open the document
            document.open();

            // Set up font for the title
            Font fontTitle = FontFactory.getFont(FontFactory.COURIER_BOLD);
            fontTitle.setSize(16);

            // Title
            Paragraph title = new Paragraph("Diagnosis: " + diagnosisEntity.getCreatedAt(), fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setSpacingAfter(20);

            // Patient information
            Paragraph patient = new Paragraph("Patient: " + diagnosisEntity.getPatient().getName()
                    + " | Email: " + diagnosisEntity.getPatient().getEmail()
                    + " | Numero Utente de Saude: " + diagnosisEntity.getPatient().getNus());

            // Professional information
            Paragraph professional = new Paragraph("Professional: " + diagnosisEntity.getProfessional().getName()
                    + " | Email: " + diagnosisEntity.getProfessional().getEmail()
                    + " | Numero Utente de Saude: " + diagnosisEntity.getProfessional().getNus());

            // Diagnosis and Prescriptions
            Paragraph diagnosis = new Paragraph("Diagnosis: " + diagnosisEntity.getDiagnosis());
            Paragraph prescriptions = new Paragraph("Prescriptions: " + diagnosisEntity.getMedications());

            // QR Code
            String medication = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            byte[] qrCodeImage = null;
            try {
                qrCodeImage = QRCodeGenerator.getQRCodeImage(medication, 100, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Image qrCode = Image.getInstance(qrCodeImage);
            qrCode.setAlignment(Paragraph.ALIGN_BOTTOM);

            // Add elements to the document
            document.addCreationDate();
            document.addAuthor("Careline");
            document.addHeader("Diagnosis", "Diagnosis");
            document.add(title);
            document.add(patient);
            document.add(professional);
            document.add(diagnosis);
            document.add(prescriptions);
            document.add(qrCode);

            // Close the document
            document.close();
        }

        // Return the generated PDF as a byte array
        return byteArrayOutputStream.toByteArray();
    }

}