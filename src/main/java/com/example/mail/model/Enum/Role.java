package com.example.mail.model.Enum;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER("USER"),
    OPERATOR("OPERATOR"),
    ADMIN("ADMIN");

    final String value;
    @Override
    public String getAuthority() {
        return value;
    }
}
