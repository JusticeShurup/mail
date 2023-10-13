package com.example.mail.model.domain;

import com.example.mail.model.Enum.MovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@Table (name = "\"MovemementHistory\"")
public class MovementHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "mail_department_id", nullable = false)
    private MailDepartment mailDepartment;

    @ManyToOne
    @JoinColumn (name = "postal_item_id", nullable = false)
    private PostalItem postalItem;

    @Enumerated(EnumType.STRING)
    @Column (name = "movement_type", nullable = false)
    private MovementType movementType;

    public  MovementHistory() {}
    public MovementHistory(MailDepartment mailDepartment, PostalItem postalItem, MovementType movementType) {
        this.mailDepartment = mailDepartment;
        this.postalItem = postalItem;
        this.movementType = movementType;
    }


}
