package com.example.mail.model.domain;

import com.example.mail.model.Enum.PostalItemType;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "postal_type", nullable = false)
    private PostalItemType postalType;
    @Column(name = "recipient_index", nullable = false)
    private String recipientIndex;
    @Column(name = "recipient_address", nullable = false)
    private String recipientAddress;
    @Column(name = "recipient_name", nullable = false)
    private String recipientName;
    @Column(name = "is_taken", nullable = false)
    @Value("${some.key=false}")
    private boolean taken;

    @ManyToOne
    @JoinColumn (name = "mail_department_id")
    private MailDepartment mailDepartment;

    @OneToMany (mappedBy = "postalItem")
    @JsonIgnore
    private List<MovementHistory> movementHistoryList;

    public PostalItem() {}
    public PostalItem(PostalItemType postalType, String recipientIndex, String recipientAddress, String recipientName, MailDepartment mailDepartment, boolean isTaken) {
        this.postalType = postalType;
        this.recipientIndex = recipientIndex;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.mailDepartment = mailDepartment;
        taken = isTaken;
    }

    public long getId() {
        return id;
    }

    public  PostalItemType getPostalType() {
        return postalType;
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

    public List<MovementHistory> getMovementHistoryList() {
        return movementHistoryList;
    }

    public void setMailDepartment(MailDepartment mailDepartment) {
        this.mailDepartment = mailDepartment;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean flag) {
        taken = flag;
    }

}
