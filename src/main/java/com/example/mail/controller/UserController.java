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
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    PostalItemServiceImpl postalItemService;

    @Autowired
    MailDepartmentServiceImpl mailDepartmentService;

    UserRepository userRepository;



    @GetMapping ("/getPostalItems")
    public ArrayList<PostalItem> getUserPostalItems(Authentication authentication) {
        Optional<User> user =  userRepository.findByUsername(authentication.getName());

        return new ArrayList<PostalItem>();
    }
}
