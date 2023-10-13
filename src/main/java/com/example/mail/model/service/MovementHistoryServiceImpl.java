package com.example.mail.model.service;

import com.example.mail.model.domain.MovementHistory;
import com.example.mail.model.repository.MovementHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovementHistoryServiceImpl implements MovementHistoryService{

    @Autowired
    MovementHistoryRepository movementHistoryRepository;
    @Override
    public void save(MovementHistory movementHistory) {
        movementHistoryRepository.save(movementHistory);
    }
}
