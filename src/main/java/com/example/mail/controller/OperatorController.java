package com.example.mail.controller;

import com.example.mail.model.domain.User;
import com.example.mail.model.service.MailDepartmentServiceImpl;
import com.example.mail.model.service.PostalItemServiceImpl;
import com.example.mail.model.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final ObjectMapper jsonFormatter = new ObjectMapper();
    @GetMapping("/getConsiderationToTakePostalItems")
    public ResponseEntity<String> getConsiderationToTakePostalItems(Authentication authentication) throws JsonProcessingException {
        Optional<User> user = userService.getUserByUsername(authentication.getName());

        if (user.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String response = jsonFormatter.writeValueAsString(postalItemService
                .getConsiderationToRegistryPostalItemListByMailDepartmentId(
                        user.get().getMailDepartment().getId()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
