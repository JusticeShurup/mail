package com.example.mail.controller;

import com.example.mail.model.Enum.MovementType;
import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.MovementHistory;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.service.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/mail")
public class MailController {

    @Autowired
    private MailDepartmentServiceImpl mailDepartmentService;

    @Autowired
    private PostalItemServiceImpl postalItemService;

    @Autowired
    private MovementHistoryServiceImpl movementHistoryService;

    private final ObjectMapper jsonFormatter = new ObjectMapper();

    @GetMapping("/getMailDepartments")
    public  ResponseEntity<String> getMailDepartments() throws IOException {
        try {

            List<MailDepartment> mailDepartments = mailDepartmentService.getMailDepartmentList();
            String answer = jsonFormatter.writeValueAsString(mailDepartments);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }
        catch (Exception ex) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPostalItemsByRecipientName")
    public  ResponseEntity<String> getPostalItemsByRecipientName(String recipientName) throws JsonProcessingException {
        return new ResponseEntity<>(jsonFormatter.writeValueAsString(postalItemService.getPostalItemListByRecipientName(recipientName)), HttpStatus.OK);
    }

    @GetMapping("/getPostalItemsBySenderName")
    public  ResponseEntity<String> getPostalItemsBySenderName(String recipientName) throws JsonProcessingException {

        return new ResponseEntity<>(jsonFormatter.writeValueAsString(postalItemService.getPostalItemListBySenderName(recipientName)), HttpStatus.OK);
    }

    @RequestMapping(
            path = "/registryPostalItem",
            method = RequestMethod.POST
    )
    public ResponseEntity<String> registryPostalItem(@RequestBody String payload) throws IOException {
        // The payload parameter contains the JSON string from the request body
        try {
            PostalItem postalItem = jsonFormatter.readValue(payload, PostalItem.class);

            if (mailDepartmentService.getMailDepartmentByIndex(postalItem.getRecipientIndex()).isEmpty()) {
                return new ResponseEntity<>("Mail Department doesn't exists", HttpStatus.BAD_REQUEST);
            }

            MovementHistory movementHistory = new MovementHistory(postalItem.getMailDepartment(), postalItem, MovementType.REGISTERED);
            postalItemService.save(postalItem);
            movementHistoryService.save(movementHistory);
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Postal item is registered", HttpStatus.OK);
    }

    @GetMapping("/getPostalItems")
    public  ResponseEntity<String> getPostalItems() throws IOException {
        try {
            List<PostalItem> postalItems = postalItemService.getPostalItemList();
            String answer = jsonFormatter.writeValueAsString(postalItems);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }
        catch (Exception ex) {
            return  new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPostalItemMovementHistory")
    public ResponseEntity<String> getPostalItemMovementHistory(@RequestParam(name = "postalItemId") int postalItemId) {
        try {
            Optional<PostalItem> postalItem = postalItemService.getPostalItemById(postalItemId);
            if (postalItem.isEmpty()) {
                return new ResponseEntity<>("Postal item not found", HttpStatus.BAD_REQUEST);
            }

            String movementHistory = jsonFormatter.writeValueAsString(postalItem.get().getMovementHistoryList());

            return new ResponseEntity<>(movementHistory, HttpStatus.OK);
        }
        catch (Exception ex) {
            return  new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPostalItemsMovementHistory")
    public ResponseEntity<String> getPostalItemsMovementHistory() {
        try {
            List<PostalItem> postalItems = postalItemService.getPostalItemList();
            ArrayList<List<MovementHistory>> movementHistories = new ArrayList<List<MovementHistory>>();
            for (var postalItem : postalItems) {
                movementHistories.add(postalItem.getMovementHistoryList());
            }
            return new ResponseEntity<>(jsonFormatter.writeValueAsString(movementHistories).toString(), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
