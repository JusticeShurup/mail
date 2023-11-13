package com.example.mail.model.service;

import com.example.mail.model.domain.Operator;
import com.example.mail.model.repository.OperatorRepository;
import com.example.mail.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    OperatorRepository operatorRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void save(Operator operator) {
        operatorRepository.save(operator);
    }

    @Override
    public Optional<Operator> getOperatorByUsername(String username) {
        var user = userRepository.findByUsername(username);
        return user.flatMap(value -> operatorRepository.findById(value.getId()));
    }
}
