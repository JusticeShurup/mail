package com.example.mail.model.domain;

import com.example.mail.model.Enum.MovementType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Entity
@Data
@Table (name = "\"MovementHistory\"")
public class MovementHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonSerialize
    private Long id;

    @ManyToOne
    @JoinColumn (name = "mail_department_id", nullable = false)
    @JsonSerialize
    private MailDepartment mailDepartment;

    @ManyToOne
    @JoinColumn (name = "postal_item_id", nullable = false)
    @JsonSerialize
    private PostalItem postalItem;

    @Enumerated(EnumType.STRING)
    @Column (name = "movement_type", nullable = false)
    @JsonSerialize
    private MovementType movementType;

    public  MovementHistory() {}
    public MovementHistory(MailDepartment mailDepartment, PostalItem postalItem, MovementType movementType) {
        this.mailDepartment = mailDepartment;
        this.postalItem = postalItem;
        this.movementType = movementType;
    }



}
