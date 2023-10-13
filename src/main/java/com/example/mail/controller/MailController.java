package com.example.mail.controller;

import com.example.mail.model.Enum.MovementType;
import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.MovementHistory;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.repository.MovementHistoryRepository;
import com.example.mail.model.service.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Objects;

@RestController
public class MailController {

    @Autowired
    private MailDepartmentServiceImpl mailDepartmentService;

    @Autowired
    private PostalItemServiceImpl postalItemService;

    @Autowired
    private MovementHistoryServiceImpl movementHistoryService;

    @PostMapping("/registryPostalItem")
    public ResponseEntity<String> registryPostalItem(@RequestBody String payload) throws IOException {
        // The payload parameter contains the JSON string from the request body
        try {
            ObjectMapper jsonFormater = new ObjectMapper();
            PostalItem postalItem = jsonFormater.readValue(payload, PostalItem.class);
            postalItemService.save(postalItem);

            MovementHistory movementHistory = new MovementHistory(postalItem.getMailDepartment(), postalItem, MovementType.REGISTERED);
            movementHistoryService.save(movementHistory);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Postal item is registered", HttpStatus.OK);
    }

    @PostMapping("/transferPostalItemToMailDepartment")
    public ResponseEntity<String> transferPostalItemToMailDepartment(@RequestParam(name = "postalItemId") long postalItemId, @RequestParam(name = "mailDepartmentId") long mailDepartmentId) {
        try {
            PostalItem postalItem = postalItemService.getPostalItemById(postalItemId).get();
            MailDepartment currentMailDepartment = postalItem.getMailDepartment();
            MailDepartment transerMailDepartment = mailDepartmentService.getMailDepartmentById(mailDepartmentId).get();

            if (currentMailDepartment == transerMailDepartment) {
                return new ResponseEntity<>("Postal item already in this mail department", HttpStatus.BAD_REQUEST);
            }

            MovementHistory movementHistoryCurrentMailDepartment = new MovementHistory(currentMailDepartment, postalItem, MovementType.DEPARTED);
            MovementHistory movementHistoryTransferMailDepartment;
            if (!Objects.equals(transerMailDepartment.getIndex(), postalItem.getRecipientIndex())) {
                movementHistoryTransferMailDepartment = new MovementHistory(transerMailDepartment, postalItem, MovementType.REDIRECTED);
            }
            else {
                System.out.println("Письмо было доставлено");
                movementHistoryTransferMailDepartment = new MovementHistory(transerMailDepartment, postalItem, MovementType.DELIVERED);
                postalItem.setDelivered(true);
                postalItem.setMailDepartment(transerMailDepartment);
            }


            movementHistoryService.save(movementHistoryCurrentMailDepartment);
            movementHistoryService.save(movementHistoryTransferMailDepartment);
            postalItemService.save(postalItem);

        }
        catch (Exception ex) {
            return  new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Postal item is transfered", HttpStatus.OK);
    }


}
