package pt.ipleiria.careline.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.repositories.PatientRepository;
import pt.ipleiria.careline.repositories.ProfessionalRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<PatientEntity> patient = patientRepository.findByNus(username);
                if (patient.isPresent()) {
                    return patient.get();
                }
                Optional<ProfessionalEntity> professional = professionalRepository.findByNus(username);
                if (professional.isPresent()) {
                    return professional.get();
                }
                throw new UsernameNotFoundException("User not found");
            }
        };
    }
}
