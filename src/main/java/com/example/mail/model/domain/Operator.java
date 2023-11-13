package com.example.mail.model.domain;

import com.example.mail.model.Enum.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"Operators\"")
@PrimaryKeyJoinColumn(name = "id")
public class Operator extends User {
    @ManyToOne
    @JoinColumn(name = "mail_department_id", nullable = false)
    MailDepartment mailDepartment;

    public Operator(Long id, String firstname, String lastname, String username, String password, String refreshToken, Role role, MailDepartment mailDepartment) {
        super(id, firstname, lastname, username, password, refreshToken, role);
        this.mailDepartment = mailDepartment;
    }

    public Operator(MailDepartment mailDepartment) {
        this.mailDepartment = mailDepartment;
    }

    public Operator() {

    }
}
