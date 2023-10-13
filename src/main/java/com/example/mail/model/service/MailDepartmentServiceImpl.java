package com.example.mail.model.service;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.repository.MailDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailDepartmentServiceImpl implements MailDepartmentService {

    @Autowired
    MailDepartmentRepository mailDepartmentRepository;


    @Override
    public void save(MailDepartment mailDepartment) {
        mailDepartmentRepository.save(mailDepartment);
    }

    @Override
    public Optional<MailDepartment> getMailDepartmentById(long id) {
        return mailDepartmentRepository.findById(id);
    }
    @Override
    public List<MailDepartment> getMailDepartmentList() {
        return mailDepartmentRepository.findAll();
    }
}
