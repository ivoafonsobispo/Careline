package pt.ipleiria.careline.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.repositories.PatientRepository;
import pt.ipleiria.careline.repositories.ProfessionalRepository;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.ProfessionalService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientService patientService;
    private final ProfessionalService professionalService;

    @Override
    public void run(String... args) throws Exception {

        if (patientRepository.count() == 0) {
            PatientEntity patient = new PatientEntity("patient", "123456789", "patient@patient.com", passwordEncoder.encode("password"));
            patientService.save(patient);
            log.debug("created Patient user - {}", patient);
        }

        if (professionalRepository.count() == 0) {
            ProfessionalEntity professional = new ProfessionalEntity("professional", "987654321", "professional@professional.com", passwordEncoder.encode("password"));
            professionalService.save(professional);
            log.debug("created Professional user - {}", professional);
        }
    }

}