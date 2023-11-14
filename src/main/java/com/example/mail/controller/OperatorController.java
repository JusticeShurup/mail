package com.example.mail.controller;

import com.example.mail.model.Enum.MovementType;
import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.MovementHistory;
import com.example.mail.model.domain.Operator;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/operator")
public class OperatorController {

    @Autowired
    PostalItemServiceImpl postalItemService;

    @Autowired
    MailDepartmentServiceImpl mailDepartmentService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    OperatorServiceImpl operatorService;

    @Autowired
    private MovementHistoryServiceImpl movementHistoryService;

    private final ObjectMapper jsonFormatter = new ObjectMapper();

    @GetMapping("/getMailDepartmentPostalItems")
    public ResponseEntity<String> getOperatorPostalItems(Authentication authentication) throws JsonProcessingException {
        Operator operator = operatorService.getOperatorByUsername(authentication.getName()).get();

        var postalItemsList = operator.getMailDepartment().getPostalItemList();
        if (postalItemsList.isEmpty()) {
            return new ResponseEntity<>("There are no postal items", HttpStatus.OK);
        }

        String response = jsonFormatter.writeValueAsString(postalItemsList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAnotherMailDepartments")
    public ResponseEntity<String> getAnotherMailDepartments(Authentication authentication) throws JsonProcessingException {
        Operator operator = operatorService.getOperatorByUsername(authentication.getName()).get();

        MailDepartment currentMailDepartment = operator.getMailDepartment();

        List<MailDepartment> mailDepartmentList = mailDepartmentService.getMailDepartmentList();
        mailDepartmentList.remove(currentMailDepartment);

        String response = jsonFormatter.writeValueAsString(mailDepartmentList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getConsiderationToRegistryPostalItems")
    public ResponseEntity<String> getConsiderationToRegistryPostalItems(Authentication authentication) throws JsonProcessingException {
        Operator operator = operatorService.getOperatorByUsername(authentication.getName()).get();

        String response = jsonFormatter.writeValueAsString(postalItemService
                .getConsiderationToRegistryPostalItemListByMailDepartmentId(
                        operator.getMailDepartment().getId()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/confirmRegistryPostalItem")
    public ResponseEntity<String> confirmRegistryPostalItem(
            Authentication authentication,
            @RequestParam long postalItemId) {
        Operator operator = operatorService.getOperatorByUsername(authentication.getName()).get();
        Optional<PostalItem> postalItem = postalItemService.getPostalItemById(postalItemId);

        if (postalItem.isEmpty()) {
            return new ResponseEntity<>("Postal item doesn't exists", HttpStatus.BAD_REQUEST);
        }
        else if (postalItem.get().getCurrentMovementHistory().getMovementType() != MovementType.CONSIDERATIONTOREGISTRY) {
            return new ResponseEntity<>("Postal item isn't allowed to registry", HttpStatus.BAD_REQUEST);
        }

        if (postalItem.get().getMailDepartment() != operator.getMailDepartment()) {
            return new ResponseEntity<>("You are not allowed to confirm this Postal Item", HttpStatus.BAD_REQUEST);
        }

        MovementHistory movementHistory = new MovementHistory(
                postalItem.get().getMailDepartment(),
                postalItem.get(),
                MovementType.REGISTERED);
        movementHistoryService.save(movementHistory);

        return new ResponseEntity<>("Postal Item successfully registered", HttpStatus.OK);
    }

    @PostMapping("/declineRegistryPostalItem")
    public ResponseEntity<String> declineRegistryPostalItem(
            Authentication authentication,
            @RequestParam long postalItemId) {
        Operator operator = operatorService.getOperatorByUsername(authentication.getName()).get();
        Optional<PostalItem> postalItem = postalItemService.getPostalItemById(postalItemId);

        if (postalItem.isEmpty()) {
            return new ResponseEntity<>("Postal item doesn't exists", HttpStatus.BAD_REQUEST);
        }
        else if (postalItem.get().getCurrentMovementHistory().getMovementType() != MovementType.CONSIDERATIONTOREGISTRY) {
            return new ResponseEntity<>("Postal item isn't allowed to decline", HttpStatus.BAD_REQUEST);
        }

        if (postalItem.get().getMailDepartment() != operator.getMailDepartment()) {
            return new ResponseEntity<>("You are not allowed to decline this Postal Item", HttpStatus.BAD_REQUEST);
        }

        MovementHistory movementHistory = new MovementHistory(
                postalItem.get().getMailDepartment(),
                postalItem.get(),
                MovementType.DECLINED);
        movementHistoryService.save(movementHistory);

        return new ResponseEntity<>("Postal Item successfully declined", HttpStatus.OK);
    }

    @GetMapping ("/getConsiderationToTakePostalItems")
    public ResponseEntity<String> getConsiderationToTakePostalItems(Authentication authentication) throws JsonProcessingException {
        Optional<Operator> operator = operatorService.getOperatorByUsername(authentication.getName());

        if (operator.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String response = jsonFormatter.writeValueAsString(postalItemService
                .getConsiderationToTakePostalItemListByMailDepartmentId(
                        operator.get().getMailDepartment().getId()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping ("/transferPostalItem")
    public ResponseEntity<String> transferPostalItem(
            Authentication authentication,
            @RequestParam long postalItemId,
            @RequestParam long transferMailDepartmentId) {
        Optional<Operator> operator = operatorService.getOperatorByUsername(authentication.getName());

        Optional<PostalItem> postalItem = postalItemService.getPostalItemById(postalItemId);
        if (postalItem.isEmpty()) {
            return new ResponseEntity<>("Postal item doesn't exists",HttpStatus.BAD_REQUEST);
        }
        else if (postalItem.get().getCurrentMovementHistory().getMovementType() == MovementType.CONSIDERATIONTOREGISTRY
        || postalItem.get().getCurrentMovementHistory().getMovementType() == MovementType.CONSIDERATIONTOTAKE
        || postalItem.get().getCurrentMovementHistory().getMovementType() == MovementType.DECLINED
        || postalItem.get().getCurrentMovementHistory().getMovementType() == MovementType.TAKEN) {
            return new ResponseEntity<>("You can't transfer this postal item",HttpStatus.BAD_REQUEST);
        }

        MailDepartment currentMailDepartment = postalItem.get().getMailDepartment();

        if (currentMailDepartment != operator.get().getMailDepartment()) {
            return new ResponseEntity<>("You not allowed to transfer postal items from this mail department",HttpStatus.BAD_REQUEST);
        }

        Optional<MailDepartment> transferMailDepartment = mailDepartmentService.getMailDepartmentById(transferMailDepartmentId);

        if (transferMailDepartment.isEmpty()) {
            return new ResponseEntity<>("transfer mail department doesn't exists",HttpStatus.BAD_REQUEST);
        }

        if (currentMailDepartment == transferMailDepartment.get()) {
            return new ResponseEntity<>("Postal item already in this mail department", HttpStatus.BAD_REQUEST);
        }

        MovementHistory movementHistoryCurrentMailDepartment = new MovementHistory(currentMailDepartment, postalItem.get(), MovementType.DEPARTED);
        MovementHistory movementHistoryTransferMailDepartment;
        if (!Objects.equals(transferMailDepartment.get().getIndex(), postalItem.get().getRecipientIndex())) {
            movementHistoryTransferMailDepartment = new MovementHistory(transferMailDepartment.get(), postalItem.get(), MovementType.REDIRECTED);
        }
        else {
            movementHistoryTransferMailDepartment = new MovementHistory(transferMailDepartment.get(), postalItem.get(), MovementType.DELIVERED);
        }
        postalItem.get().setMailDepartment(transferMailDepartment.get());


        movementHistoryService.save(movementHistoryCurrentMailDepartment);
        movementHistoryService.save(movementHistoryTransferMailDepartment);
        postalItemService.save(postalItem.get());

        return new ResponseEntity<>("Postal Item successfully transferred", HttpStatus.OK);
    }

    @PostMapping("/confirmTakePostalItem")
    public ResponseEntity<String> confirmTakePostalItem(
            Authentication authentication,
            @RequestParam long postalItemId) {
        Operator operator = operatorService.getOperatorByUsername(authentication.getName()).get();
        Optional<PostalItem> postalItem = postalItemService.getPostalItemById(postalItemId);

        if (postalItem.isEmpty()) {
            return new ResponseEntity<>("Postal item doesn't exists", HttpStatus.BAD_REQUEST);
        }
        else if (postalItem.get().getCurrentMovementHistory().getMovementType() != MovementType.CONSIDERATIONTOTAKE) {
            return new ResponseEntity<>("Postal item isn't allowed to be taken", HttpStatus.BAD_REQUEST);
        }

        if (postalItem.get().getMailDepartment() != operator.getMailDepartment()) {
            return new ResponseEntity<>("You are not allowed to give out this Postal Item", HttpStatus.BAD_REQUEST);
        }

        MovementHistory movementHistory = new MovementHistory(
                postalItem.get().getMailDepartment(),
                postalItem.get(),
                MovementType.TAKEN);
        movementHistoryService.save(movementHistory);

        return new ResponseEntity<>("Postal item is successfully taken", HttpStatus.OK);
    }
}