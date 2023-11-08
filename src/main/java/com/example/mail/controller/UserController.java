package com.example.mail.controller;

import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.domain.User;
import com.example.mail.model.repository.UserRepository;
import com.example.mail.model.service.MailDepartmentServiceImpl;
import com.example.mail.model.service.PostalItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    PostalItemServiceImpl postalItemService;

    @Autowired
    MailDepartmentServiceImpl mailDepartmentService;

    UserRepository userRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test(Authentication authentication) {
        return  new ResponseEntity<>(authentication.getName(), HttpStatus.OK);
    }

    @GetMapping ("/getPostalItems")
    public ArrayList<PostalItem> getUserPostalItems(Principal principal) {

        return new ArrayList<PostalItem>();
    }
}
