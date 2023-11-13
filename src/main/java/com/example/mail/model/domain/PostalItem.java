package com.example.mail.model.domain;

import com.example.mail.model.Enum.PostalItemType;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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


    @Column(name = "sender_name", nullable = false)
    private String senderName;


    @Column(name = "is_taken", nullable = false)
    @Value("${some.key=false}")
    private boolean taken;


    @ManyToOne
    @JoinColumn (name = "mail_department_id", nullable = false)
    private MailDepartment mailDepartment;


    @OneToMany (mappedBy = "postalItem")
    @JsonIgnore
    private List<MovementHistory> movementHistoryList;



}
