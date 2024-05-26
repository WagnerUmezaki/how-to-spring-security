package com.howto.security.controller.auth;

import com.howto.security.controller.auth.request.AuthenticationRequest;
import com.howto.security.controller.auth.request.RegisterRequest;
import com.howto.security.controller.auth.response.AuthenticationResponse;
import com.howto.security.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody
            final RegisterRequest registerRequest) {
        authenticationService.registerUser(
                registerRequest.getNickname(),
                registerRequest.getEmail(),
                registerRequest.getPassword());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody
            final AuthenticationRequest registerRequest) {
        final String token = authenticationService.authenticate(
                registerRequest.getEmail(), registerRequest.getPassword());
        return ResponseEntity.ok(AuthenticationResponse.builder().token(token).build());
    }

}
