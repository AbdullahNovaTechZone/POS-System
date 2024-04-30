package com.novatechzone.pos.domain.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogInDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
