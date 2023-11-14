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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> registryOperator(Authentication authentication, @RequestBody String payload) throws JsonProcessingException {
            Map<String, Object> payloadMap = jsonFormatter.readValue(payload, new TypeReference<Map<String, Object>>() {});

            var registerRequest = RegisterRequest.builder()
                    .firstname((String) payloadMap.get("firstname"))
                    .lastname((String) payloadMap.get("lastname"))
                    .username((String) payloadMap.get("username"))
                    .password((String)  payloadMap.get("password"))
                    .role(Role.valueOf((String) payloadMap.get("role")))
                    .build();

            // Java can't cast Integer to Long when it's deserialize from Jackson (Maybe I'm stupid LoL)
            Integer mailDepartmentIdInteger = (Integer) payloadMap.get("mailDepartmentId");
            long mailDepartmentIdLong = mailDepartmentIdInteger.longValue();

            var mailDepartment = mailDepartmentService.getMailDepartmentById(mailDepartmentIdLong);

            if (mailDepartment.isEmpty()) {
                return new ResponseEntity<>(
                        "Mail Department for this operator doesn't exists",
                        HttpStatus.BAD_REQUEST);
            }


            var user = authenticationService.registerAndReturnUser(registerRequest);

            Operator operator = new Operator(user.getId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), user.getRefreshToken(), user.getRole(), mailDepartment.get());

            operatorService.save(operator);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
