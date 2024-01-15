package pt.ipleiria.careline.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.dto.JwtAuthenticationResponse;
import pt.ipleiria.careline.domain.dto.SignInRequest;
import pt.ipleiria.careline.domain.dto.SignUpRequest;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.domain.enums.UserType;
import pt.ipleiria.careline.repositories.PatientRepository;
import pt.ipleiria.careline.repositories.ProfessionalRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request, UserType userType) {
        if ((userType != UserType.PATIENT && userType != UserType.PROFESSIONAL)) {
            log.atError().log("Invalid user type - {}", userType);
            throw new IllegalArgumentException("Invalid user type");
        }

        var jwt = "";
        if (userType == UserType.PATIENT) {
            PatientEntity patient = new PatientEntity(request.getName(), request.getNus(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
            patientRepository.save(patient);
            jwt = jwtService.generateToken(patient);
            log.atInfo().log("Created Patient user - {}", patient);
        } else { // UserType.PROFESSIONAL
            ProfessionalEntity professional = new ProfessionalEntity(request.getName(), request.getNus(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
            professionalRepository.save(professional);
            jwt = jwtService.generateToken(professional);
            log.atInfo().log("Created Professional user - {}", professional);
        }

        if (jwt.isEmpty()) {
            log.atError().log("Token Empty: Invalid email - {} or password - {}", request.getEmail(), request.getPassword());
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }


    public JwtAuthenticationResponse signin(SignInRequest request, UserType userType) {
        if ((userType != UserType.PATIENT && userType != UserType.PROFESSIONAL)) {
            log.atError().log("Invalid user type - {}", userType);
            throw new IllegalArgumentException("Invalid user type");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNus(), request.getPassword()));

        var jwt = "";
        if (userType == UserType.PATIENT) {
            Optional<PatientEntity> patient = patientRepository.findByNus(request.getNus());
            if (patient.isEmpty()) {
                log.atError().log("Invalid NUS - {}, or password - {}", request.getNus(), request.getPassword());
                throw new IllegalArgumentException("Invalid NUS or password.");
            }
            PatientEntity patientEntity = patient.get();
            jwt = jwtService.generateToken(patientEntity);
        } else { // UserType.PROFESSIONAL
            Optional<ProfessionalEntity> professional = professionalRepository.findByNus(request.getNus());
            if (professional.isEmpty()) {
                log.atError().log("Invalid NUS - {}, or password - {}", request.getNus(), request.getPassword());
                throw new IllegalArgumentException("Invalid NUS or password.");
            }
            ProfessionalEntity professionalEntity = professional.get();
            jwt = jwtService.generateToken(professionalEntity);
        }

        if (jwt.isEmpty()) {
            log.atError().log("Token Empty: Invalid NUS - {}, or password - {}", request.getNus(), request.getPassword());
            throw new IllegalArgumentException("Invalid NUS or password.");
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
