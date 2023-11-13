package com.example.mail.model.service;

import com.example.mail.model.domain.Operator;
import com.example.mail.model.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    OperatorRepository operatorRepository;

    @Override
    public void save(Operator operator) {
        operatorRepository.save(operator);
    }
}
