package com.novatechzone.pos.domain.security.service;

import com.novatechzone.pos.domain.security.domain.OwnerData;
import com.novatechzone.pos.domain.security.dto.AuthResponseDTO;
import com.novatechzone.pos.domain.security.dto.LogInDTO;
import com.novatechzone.pos.domain.security.dto.OwnerDTO;
import com.novatechzone.pos.domain.security.entity.Owner;
import com.novatechzone.pos.domain.security.repos.OwnerRepository;
import com.novatechzone.pos.domain.security.util.JwtTokenUtil;
import com.novatechzone.pos.dto.ApplicationResponseDTO;
import com.novatechzone.pos.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final OwnerRepository ownerRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public ApplicationResponseDTO signup(OwnerDTO ownerDTO) {
        if (ownerRepository.findByUsername(ownerDTO.getUsername()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USERNAME_ALREADY_EXIST", "Username Already Exist");
        } else {
            ownerRepository.save(
                    Owner.builder()
                            .name(ownerDTO.getName())
                            .username(ownerDTO.getUsername())
                            .password(passwordEncoder.encode(ownerDTO.getPassword()))
                            .build()
            );
            return new ApplicationResponseDTO(HttpStatus.CREATED, "SIGNUP_SUCCESS", "Sign Up Success");
        }
    }

    public AuthResponseDTO login(LogInDTO logInDTO) {
        Optional<Owner> optionalUser = ownerRepository.findByUsername(logInDTO.getUsername());
        if (optionalUser.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME", "Invalid Username");
        } else {
            Owner owner = optionalUser.get();
            if (!passwordEncoder.matches(logInDTO.getPassword(), owner.getPassword())) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "Invalid Password");
            }
            String accessToken = jwtTokenUtil.generateAccessToken(owner);
            String refreshToken = jwtTokenUtil.generateRefreshToken(owner);
            return new AuthResponseDTO(HttpStatus.OK, "LOGIN_SUCCESS", "Login Success", accessToken, refreshToken);
        }
    }

    public static String getCurrentUser() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (securityContext != null && securityContext.getAuthentication() != null) {
                Object principal = securityContext.getAuthentication().getPrincipal();
                if (principal instanceof OwnerData ownerData) {
                    return ownerData.getUsername();
                } else {
                    throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_PRINCIPAL", "Invalid Principal");
                }
            } else {
                throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "SECURITY_CONTEXT_IS_NULL", "Security Context is Null");
            }
        } catch (Exception e) {
            throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_USER", e.getMessage());
        }
    }

    public AuthResponseDTO generateRefreshToken(String refreshToken) {
        if (jwtTokenUtil.validateToken(refreshToken)) {
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
            Owner owner = ownerRepository.findByUsername(username)
                    .orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "User not Found"));
            String accessToken = jwtTokenUtil.generateAccessToken(owner);
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(owner);
            return new AuthResponseDTO(HttpStatus.OK, "NEW_ACCESS_TOKEN_&_NEW_REFRESH_TOKEN", "New Access & Refresh Token", accessToken, newRefreshToken);
        }
        throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN", "Invalid Refresh Token");
    }
}
