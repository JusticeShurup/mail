package com.example.mail.controller;

import com.example.mail.model.Enum.Role;
import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.Operator;
import com.example.mail.model.domain.RegisterRequest;
import com.example.mail.model.service.AuthenticationService;
import com.example.mail.model.service.MailDepartmentServiceImpl;
import com.example.mail.model.service.OperatorService;
import com.example.mail.model.service.OperatorServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    OperatorServiceImpl operatorService;

    @Autowired
    MailDepartmentServiceImpl mailDepartmentService;

    @Autowired
    AuthenticationService authenticationService;

    private final ObjectMapper jsonFormatter = new ObjectMapper();
    @PostMapping("/registerOperator")
    public ResponseEntity<String> registerOperator(
            Authentication authentication,
            @RequestBody String payload)
            throws JsonProcessingException {
            Map<String, Object> payloadMap = jsonFormatter.readValue(payload, new TypeReference<Map<String, Object>>() {});

            var registerRequest = RegisterRequest.builder()
                    .firstname((String) payloadMap.get("firstname"))
                    .lastname((String) payloadMap.get("lastname"))
                    .username((String) payloadMap.get("username"))
                    .password((String)  payloadMap.get("password"))
                    .role(Role.OPERATOR)
                    .build();

            // Java can't cast Integer to Long when it's deserialize from Jackson (Maybe I'm stupid LoL)
            Integer mailDepartmentIdInteger = (Integer) payloadMap.get("mailDepartmentId");
            Long mailDepartmentIdLong = mailDepartmentIdInteger.longValue();

            var mailDepartment = mailDepartmentService.getMailDepartmentById(mailDepartmentIdLong);

            if (mailDepartment.isEmpty()) {
                return new ResponseEntity<>(
                        "Mail Department for this operator doesn't exists",
                        HttpStatus.BAD_REQUEST);
            }

            try {
                var user = authenticationService.registerAndReturnUser(registerRequest);

                Operator operator = new Operator(user.getId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), user.getRefreshToken(), user.getRole(), mailDepartment.get());

                operatorService.save(operator);
            } catch (DataIntegrityViolationException exception) {
                return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
            }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/registerMailDepartment")
    public ResponseEntity<String> registerMailDepartment(@RequestBody String mailDepartment) throws JsonProcessingException {


        JsonNode jsonNode = jsonFormatter.readTree(mailDepartment);

        MailDepartment mailDepartmentToSave = new MailDepartment(
                jsonNode.get("index").asText(),
                jsonNode.get("name").asText(),
                jsonNode.get("address").asText()
                );


        if (mailDepartmentToSave.getAddress().length() < 3) {
            return new ResponseEntity<>("Mail Department's address is invalid", HttpStatus.BAD_REQUEST);
        }

        if (mailDepartmentToSave.getIndex().length() != 6) {
            return new ResponseEntity<>("Mail Department's index is invalid", HttpStatus.BAD_REQUEST);
        }

        if (mailDepartmentToSave.getName().length() < 6) {
            return new ResponseEntity<>("Mail Department's name is invalid", HttpStatus.BAD_REQUEST);
        }

        try {
            mailDepartmentService.save(mailDepartmentToSave);
        } catch (DataIntegrityViolationException exception) {
            return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Mail Department successfully registered", HttpStatus.OK);
    }

    @GetMapping("/getMailDepartments")
    public ResponseEntity<String> getMailDepartments() throws JsonProcessingException {
        ArrayList<MailDepartment> mailDepartmentArrayList =
                (ArrayList<MailDepartment>) mailDepartmentService.getMailDepartmentList();

        String response = jsonFormatter.writeValueAsString(mailDepartmentArrayList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getOperators")
    public ResponseEntity<String> getOperators() throws JsonProcessingException {
        ArrayList<Operator> operatorArrayList = (ArrayList<Operator>) operatorService.getOperatorList();

        String response = jsonFormatter.writeValueAsString(operatorArrayList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
