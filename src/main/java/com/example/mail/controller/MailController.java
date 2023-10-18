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
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin

public class MailController {

    @Autowired
    private MailDepartmentServiceImpl mailDepartmentService;

    @Autowired
    private PostalItemServiceImpl postalItemService;

    @Autowired
    private MovementHistoryServiceImpl movementHistoryService;

    private ObjectMapper jsonFormater = new ObjectMapper();

    @GetMapping("/getMailDepartments")
    public  ResponseEntity<String> getMailDepartments() throws IOException {
        try {
            List<MailDepartment> mailDepartments = mailDepartmentService.getMailDepartmentList();
            String answer = jsonFormater.writeValueAsString(mailDepartments);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }
        catch (Exception ex) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registryPostalItem")
    public ResponseEntity<String> registryPostalItem(@RequestBody String payload) throws IOException {
        // The payload parameter contains the JSON string from the request body
        try {
            ObjectMapper jsonFormater = new ObjectMapper();
            PostalItem postalItem = jsonFormater.readValue(payload, PostalItem.class);

            if (mailDepartmentService.getMailDepartmentByIndex(postalItem.getRecipientIndex()).isEmpty()) {
                return new ResponseEntity<>("Mail Department doesn't exists", HttpStatus.BAD_REQUEST);
            }

            MovementHistory movementHistory = new MovementHistory(postalItem.getMailDepartment(), postalItem, MovementType.REGISTERED);
            postalItemService.save(postalItem);
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
                movementHistoryTransferMailDepartment = new MovementHistory(transerMailDepartment, postalItem, MovementType.DELIVERED);
            }
            postalItem.setMailDepartment(transerMailDepartment);


            movementHistoryService.save(movementHistoryCurrentMailDepartment);
            movementHistoryService.save(movementHistoryTransferMailDepartment);
            postalItemService.save(postalItem);

        }
        catch (Exception ex) {
            return  new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Postal item is transfered", HttpStatus.OK);
    }
    @PostMapping("/takePostalItemById")
    public ResponseEntity<String> takePostalItemById(@RequestParam(name = "postalItemId") long postalItemId) {
        try {
            PostalItem postalItem = postalItemService.getPostalItemById(postalItemId).get();

            if (!postalItem.getRecipientIndex().equals(postalItem.getMailDepartment().getIndex())) {
                return new ResponseEntity<>("The postal item has not been delivered yet", HttpStatus.BAD_REQUEST);
            }

            if (postalItem.isTaken()) {
                return new ResponseEntity<>("The postal item has already been collected", HttpStatus.BAD_REQUEST);
            }

            postalItem.setTaken(true);
            MovementHistory movementHistory = new MovementHistory(postalItem.getMailDepartment(), postalItem, MovementType.TAKEN);
            movementHistoryService.save(movementHistory);
        }
        catch (Exception ex) {
            return  new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Postal item successfully taken", HttpStatus.OK);
    }

    @GetMapping("/getPostalItemMovementHistoryById")
    public ResponseEntity<String> getPostalItemMovementHistoryById(@RequestParam (name = "postalItemId") Long postalItemId) {
        try {
            PostalItem postalItem = postalItemService.getPostalItemById(postalItemId).get();
            List<MovementHistory> movementHistoryList = postalItem.getMovementHistoryList();

            ObjectMapper jsonFormater = new ObjectMapper();
            String answer = jsonFormater.writeValueAsString(movementHistoryList);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }
        catch (Exception ex) {
            return  new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }
    }


}
