package com.example.mail.model.service;

import com.example.mail.model.domain.MailDepartment;

import java.util.List;
import java.util.Optional;

public interface MailDepartmentService {
    void save(MailDepartment mailDepartment);


    Optional<MailDepartment> getMailDepartmentById(long id);
    List<MailDepartment> getMailDepartmentList();
}
