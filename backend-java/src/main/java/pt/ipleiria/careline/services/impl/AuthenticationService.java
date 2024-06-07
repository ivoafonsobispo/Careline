package pt.ipleiria.careline.services.impl;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request, UserType userType) {
        if ((userType != UserType.PATIENT && userType != UserType.PROFESSIONAL)) {
            throw new IllegalArgumentException("Invalid user type");
        }

        var jwt = "";
        if (userType == UserType.PATIENT) {
            PatientEntity patient = new PatientEntity(request.getName(), request.getNus(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
            patientRepository.save(patient);
            jwt = jwtService.generateToken(patient);
        } else { // UserType.PROFESSIONAL
            ProfessionalEntity professional = new ProfessionalEntity(request.getName(), request.getNus(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
            professionalRepository.save(professional);
            jwt = jwtService.generateToken(professional);
        }

        if (jwt.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }


    public JwtAuthenticationResponse signin(SignInRequest request, UserType userType) {
        if ((userType != UserType.PATIENT && userType != UserType.PROFESSIONAL)) {
            throw new IllegalArgumentException("Invalid user type");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNus(), request.getPassword()));

        var jwt = "";
        if (userType == UserType.PATIENT) {
            PatientEntity patient = patientRepository.findByNus(request.getNus())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid NUS or password."));
            jwt = jwtService.generateToken(patient);
        } else { // UserType.PROFESSIONAL
            ProfessionalEntity professional = professionalRepository.findByNus(request.getNus())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid NUS or password."));
            jwt = jwtService.generateToken(professional);
        }

        if (jwt.isEmpty()) {
            throw new IllegalArgumentException("Invalid NUS or password.");
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
