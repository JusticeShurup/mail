package com.example.mail.model.services;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.repositories.MailDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailDepartmentServiceImpl implements MailDepartmentService {

    @Autowired
    MailDepartmentRepository mailDepartmentRepository;
    @Override
    public void save(MailDepartment mailDepartment) {
        mailDepartmentRepository.save(mailDepartment);
    }

    @Override
    public List<MailDepartment> getMailDepartmentList() {
        return mailDepartmentRepository.findAll();
    }
}
