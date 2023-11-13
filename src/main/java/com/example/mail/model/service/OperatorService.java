package com.example.mail.model.service;

import com.example.mail.model.domain.Operator;

import java.util.Optional;

public interface OperatorService {
    void save(Operator operator);

     Optional<Operator> getOperatorByUsername(String username);
}
