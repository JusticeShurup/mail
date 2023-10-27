package com.example.mail.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
