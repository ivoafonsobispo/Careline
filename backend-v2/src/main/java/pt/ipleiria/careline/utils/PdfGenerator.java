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
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);

        Paragraph title = new Paragraph("Diagnosis: " + diagnosisEntity.getCreatedAt(), fontTitle);
        Paragraph patient = new Paragraph("Patient: " + diagnosisEntity.getPatient().getName() + " | " + diagnosisEntity.getPatient().getEmail() + " | " + diagnosisEntity.getPatient().getNus());
        Paragraph professional = new Paragraph("Professional: " + diagnosisEntity.getProfessional().getName() + " | " + diagnosisEntity.getProfessional().getEmail() + " | " + diagnosisEntity.getProfessional().getNus());
        Paragraph diagnosis = new Paragraph("Diagnosis: " + diagnosisEntity.getDiagnosis());
        Paragraph prescriptions = new Paragraph("Prescriptions: " + diagnosisEntity.getPrescriptions());

        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(patient);
        document.add(professional);
        document.add(diagnosis);
        document.add(prescriptions);
        document.close();
    }
}