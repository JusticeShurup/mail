package com.example.mail.model.services;

import com.example.mail.model.domain.MailDepartment;

import java.util.List;

public interface MailDepartmentService {
    void save(MailDepartment mailDepartment);
    List<MailDepartment> getMailDepartmentList();
}
