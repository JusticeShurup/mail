package com.example.mail.controller;

import com.example.mail.model.Enum.MovementType;
import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.MovementHistory;
import com.example.mail.model.domain.Operator;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/getConsiderationToRegistryPostalItems")
    public ResponseEntity<String> getConsiderationToRegistryPostalItems(Authentication authentication) throws JsonProcessingException {
        Optional<Operator> operator = operatorService.getOperatorByUsername(authentication.getName());

        if (operator.isEmpty()) return new ResponseEntity<>("Operator doesn't exists", HttpStatus.BAD_REQUEST);

        String response = jsonFormatter.writeValueAsString(postalItemService
                .getConsiderationToRegistryPostalItemListByMailDepartmentId(
                        operator.get().getMailDepartment().getId()));

        return new ResponseEntity<>(response, HttpStatus.OK);
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
        else if (postalItem.get().getCurrentMovementHistory().getMovementType() != MovementType.CONSIDERATIONTOREGISTRY
        && postalItem.get().getCurrentMovementHistory().getMovementType() != MovementType.CONSIDERATIONTOTAKE
        && postalItem.get().getCurrentMovementHistory().getMovementType() != MovementType.DECLINED
        && postalItem.get().getCurrentMovementHistory().getMovementType() != MovementType.TAKEN) {
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






}
