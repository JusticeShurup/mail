package com.example.mail.model.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "\"MailDepartments\"")

public class MailDepartment {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "index", nullable = false)
    private String index;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany (mappedBy = "mailDepartment")
    private List<PostalItem> postalItemList;

    protected MailDepartment() {}

    public  MailDepartment(String index, String name, String address) {
        this.id = id;
        this.index = index;
        this.name = name;
        this.address = address;
    }
    @Override
    public String toString() {
        return id + " " + index + " " + name + " " + address;
    }

}
