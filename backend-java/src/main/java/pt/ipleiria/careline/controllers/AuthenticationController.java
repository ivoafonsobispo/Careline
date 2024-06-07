package pt.ipleiria.careline.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.JwtAuthenticationResponse;
import pt.ipleiria.careline.domain.dto.SignInRequest;
import pt.ipleiria.careline.domain.dto.SignUpRequest;
import pt.ipleiria.careline.domain.enums.UserType;
import pt.ipleiria.careline.services.impl.AuthenticationService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup/patient")
    public JwtAuthenticationResponse signupPatient(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request, UserType.PATIENT);
    }

    @PostMapping("/signup/professional")
    public JwtAuthenticationResponse signupProfessional(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request, UserType.PROFESSIONAL);
    }

    @PostMapping("/signin/patient")
    public JwtAuthenticationResponse signinPatient(@RequestBody SignInRequest request) {
        return authenticationService.signin(request, UserType.PATIENT);
    }

    @PostMapping("/signin/professional")
    public JwtAuthenticationResponse signinProfessional(@RequestBody SignInRequest request) {
        return authenticationService.signin(request, UserType.PROFESSIONAL);
    }
}
