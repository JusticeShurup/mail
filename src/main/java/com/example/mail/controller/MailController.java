package com.example.mail.controller;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.services.MailDepartmentServiceImpl;
import com.example.mail.model.services.PostalItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {

    @Autowired
    private MailDepartmentServiceImpl mailDepartmentService;

    @Autowired
    private PostalItemServiceImpl postalItemService;

    @PostMapping(path = "registryPostalItem", params = {"postalType", "recipientIndex", "recipientAddress",  "recipientName", "mailDepartmentId"})
    public void registryPostalItem(@RequestParam("postalType") String postalType, @RequestParam("recipientIndex") String recipientIndex, @RequestParam("recipientAddress") String recipientAddress, @RequestParam("recipientName") String recipientName, @RequestParam("mailDepartmentId") long mailDepartmentId) {
        System.out.println(postalType);
        postalItemService.registryPostalItem(postalType, recipientIndex, recipientAddress, recipientName, mailDepartmentId, false);
    }

    @PostMapping("/process")
    public void process(@RequestBody String payload) {
        // The payload parameter contains the JSON string from the request body

    }


}
