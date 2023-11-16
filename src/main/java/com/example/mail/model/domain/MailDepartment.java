package com.example.mail.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table (name = "\"MailDepartments\"")

public class MailDepartment {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "index", nullable = false, unique = true)
    private String index;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany (mappedBy = "mailDepartment")
    @JsonIgnore
    private List<PostalItem> postalItemList;

    @OneToMany (mappedBy = "mailDepartment")
    @JsonIgnore
    private List<MovementHistory> movementHistoryList;

    @OneToMany (mappedBy = "mailDepartment")
    @JsonIgnore
    private List<Operator> operatorList;


    protected MailDepartment() {}

    public  MailDepartment(String index, String name, String address) {
        this.index = index;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return id + " " + index + " " + name + " " + address;
    }

}
