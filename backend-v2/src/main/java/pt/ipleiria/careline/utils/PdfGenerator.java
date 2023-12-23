package pt.ipleiria.careline.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;

import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {
    public void generateDiagnosisPDF(DiagnosisEntity diagnosisEntity) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        String homeDirectory = System.getProperty("user.home");
        PdfWriter.getInstance(document, new FileOutputStream(homeDirectory + "/Desktop/Report.pdf"));
        document.open();

        // Necessities
        Font fontTitle = FontFactory.getFont(FontFactory.COURIER_BOLD);
        document.addCreationDate();
        document.addAuthor("Careline");
        document.addHeader("Diagnosis", "Diagnosis");
        Paragraph title = new Paragraph("Diagnosis: " + diagnosisEntity.getCreatedAt(), fontTitle);
        Paragraph patient =
                new Paragraph("Patient: " + diagnosisEntity.getPatient().getName() + " | email: " + diagnosisEntity.getPatient().getEmail() + " | Numero Utente de Saude: " + diagnosisEntity.getPatient().getNus());
        Paragraph professional =
                new Paragraph("Professional: " + diagnosisEntity.getProfessional().getName() + " | email: " + diagnosisEntity.getProfessional().getEmail() + " | Numero Utente de Saude" + diagnosisEntity.getProfessional().getNus());
        Paragraph diagnosis = new Paragraph("Diagnosis: " + diagnosisEntity.getDiagnosis());
        Paragraph prescriptions = new Paragraph("Prescriptions: " + diagnosisEntity.getPrescriptions());

        String medication = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        byte[] qrCodeImage = null;
        try {
            qrCodeImage = QRCodeGenerator.getQRCodeImage(medication, 100, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Styling
        fontTitle.setSize(16);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        Image qrCode = Image.getInstance(qrCodeImage);
        qrCode.setAlignment(Paragraph.ALIGN_BOTTOM);
        title.setSpacingAfter(20);

        document.add(title);
        document.add(patient);
        document.add(professional);
        document.add(diagnosis);
        document.add(prescriptions);
        document.add(qrCode);
        document.close();
    }
}