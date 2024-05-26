package com.howto.security.service;

import com.howto.security.config.auth.JwtService;
import com.howto.security.entity.AppUserEntity;
import com.howto.security.entity.AppUserRole;
import com.howto.security.repository.AppUserRepository;
import com.howto.security.utils.UuidUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UuidUtils uuidUtils;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void registerUser(
            final String nickname,
            final String email,
            final String password) {
        final AppUserEntity appUserEntity = AppUserEntity.builder()
                .code(uuidUtils.randomUUUID())
                .nickname(nickname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(AppUserRole.NORMAL)
                .build();
        appUserRepository.save(appUserEntity);
    }

    public String authenticate(
            final String email,
            final String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        final AppUserEntity appUser = appUserRepository.findByEmail(email)
                .orElseThrow();
        return jwtService.generateToken(appUser);
    }
}
