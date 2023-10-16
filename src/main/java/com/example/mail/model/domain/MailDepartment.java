package com.example.mail.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
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

    @OneToMany (mappedBy = "mailDepartment")
    @JsonIgnore
    private List<MovementHistory> movementHistoryList;

    public List<MovementHistory> getMovementHistoryList() {
        return  movementHistoryList;
    }

    protected MailDepartment() {}

    public  MailDepartment(String index, String name, String address) {
        this.index = index;
        this.name = name;
        this.address = address;
    }

    public long getId () {
        return id;
    }

    public String getIndex () {
        return index;
    }

    public void setIndex (String index) {
        this.index = index;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }
    public String getAddress () {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return id + " " + index + " " + name + " " + address;
    }

}
