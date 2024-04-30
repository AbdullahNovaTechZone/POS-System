package com.novatechzone.pos.domain.security.rest;

import com.novatechzone.pos.domain.security.dto.AuthResponseDTO;
import com.novatechzone.pos.domain.security.dto.LogInDTO;
import com.novatechzone.pos.domain.security.dto.OwnerDTO;
import com.novatechzone.pos.domain.security.service.AuthService;
import com.novatechzone.pos.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {
    private final AuthService authService;

    @PostMapping("owner/signup")
    public ResponseEntity<ApplicationResponseDTO> signup(@Valid @RequestBody OwnerDTO ownerDTO) {
        return ResponseEntity.ok(authService.signup(ownerDTO));
    }

    @PostMapping("owner/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LogInDTO logInDTO) {
        return ResponseEntity.ok(authService.login(logInDTO));
    }

    @PostMapping("owner/token/refresh/{refresh-token}")
    public ResponseEntity<AuthResponseDTO> refreshAccessToken(@PathVariable("refresh-token") String refreshToken) {
        return ResponseEntity.ok(authService.generateRefreshToken(refreshToken));
    }
}
