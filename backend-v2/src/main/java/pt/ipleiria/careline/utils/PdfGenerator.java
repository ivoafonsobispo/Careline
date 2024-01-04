package pt.ipleiria.careline.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {
    private static PdfPTable drawMedicationTable(DiagnosisEntity diagnosisEntity) throws IOException {
        PdfPTable medications = new PdfPTable(5);
        medications.addCell("Name");
        medications.addCell("Dosage");
        medications.addCell("Frequency");
        medications.addCell("Duration");
        medications.addCell("QR Code");

        for (int i = 0; i < diagnosisEntity.getMedications().size(); i++) {
            medications.addCell(diagnosisEntity.getMedications().get(i).getName());
            medications.addCell(diagnosisEntity.getMedications().get(i).getDosage());
            medications.addCell(diagnosisEntity.getMedications().get(i).getFrequency());
            medications.addCell(diagnosisEntity.getMedications().get(i).getDuration());

            String medicationURL = "https://www.kuantokusta.pt/search?q=";
            medicationURL += diagnosisEntity.getMedications().get(i).getName();
            byte[] qrCodeImage = null;
            try {
                qrCodeImage = QRCodeGenerator.getQRCodeImage(medicationURL, 100, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Image qrCode = Image.getInstance(qrCodeImage);
            medications.addCell(qrCode);
        }
        return medications;
    }

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
            ZonedDateTime createdAt = ZonedDateTime.ofInstant(diagnosisEntity.getCreatedAt(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd 'AT' HH:mm");
            String formattedDate = createdAt.format(formatter);
            Paragraph title = new Paragraph("Diagnosis from: " + formattedDate, fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setSpacingAfter(20);

            // Patient information
            Paragraph patient = new Paragraph("Patient: " + diagnosisEntity.getPatient().getName()
                    + " | Email: " + diagnosisEntity.getPatient().getEmail()
                    + " | NUS: " + diagnosisEntity.getPatient().getNus());

            // Professional information
            Paragraph professional = new Paragraph("Professional: " + diagnosisEntity.getProfessional().getName()
                    + " | Email: " + diagnosisEntity.getProfessional().getEmail()
                    + " | NUS: " + diagnosisEntity.getProfessional().getNus());

            // Diagnosis
            Paragraph diagnosis = new Paragraph("Diagnosis: " + diagnosisEntity.getDiagnosis());

            // Generate Medications Table
            Paragraph medicationsParagraph = new Paragraph("Medications:");
            medicationsParagraph.setSpacingAfter(20);
            PdfPTable medications = drawMedicationTable(diagnosisEntity);

            // Add elements to the document
            document.addCreationDate();
            document.addAuthor("Careline");
            document.addHeader("Diagnosis", "Diagnosis");
            document.add(title);
            document.add(patient);
            document.add(professional);
            document.add(diagnosis);
            document.add(medicationsParagraph);
            document.add(medications);

            // Close the document
            document.close();
        }

        // Return the generated PDF as a byte array
        return byteArrayOutputStream.toByteArray();
    }


}