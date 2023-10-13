package com.example.mail.model.domain;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Entity
@Table(name = "\"PostalItems\"")
public class PostalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "postal_type", nullable = false)
    private String postalType;
    @Column(name = "recipient_index", nullable = false)
    private String recipientIndex;
    @Column(name = "recipient_address", nullable = false)
    private String recipientAddress;
    @Column(name = "recipient_name", nullable = false)
    private String recipientName;
    @Column(name = "is_delivered", nullable = false)
    @Value("${some.key=false}")
    private boolean delivered;

    @ManyToOne
    @JoinColumn (name = "mail_department_id")
    private MailDepartment mailDepartment;

    @OneToMany (mappedBy = "postalItem")
    @JsonIgnore
    private List<MovementHistory> movementHistoryList;

    public PostalItem() {}
    public PostalItem(String postalType, String recipientIndex, String recipientAddress, String recipientName, MailDepartment mailDepartment, boolean isDelivered) {
        this.postalType = postalType;
        this.recipientIndex = recipientIndex;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.mailDepartment = mailDepartment;
        delivered = isDelivered;
    }

    public long getId() {
        return id;
    }

    public  String getPostalType() {
        return  postalType;
    }
    public String getRecipientIndex() {
        return recipientIndex;
    }

    public String getRecipientAddress() {
        return  recipientAddress;
    }

    public String getRecipientName() {
        return  recipientName;
    }

    public MailDepartment getMailDepartment() {
        return mailDepartment;
    }

    public void setMailDepartment(MailDepartment mailDepartment) {
        this.mailDepartment = mailDepartment;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean flag) {
        delivered = flag;
    }

}
