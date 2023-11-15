package com.example.mail.model.service;

import com.example.mail.model.domain.Operator;

import java.util.List;
import java.util.Optional;

public interface OperatorService {
    void save(Operator operator);

    List<Operator> getOperatorList();

    Optional<Operator> getOperatorByUsername(String username);
}
