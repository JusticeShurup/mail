package com.example.mail.controller;

import com.example.mail.model.Enum.MovementType;
import com.example.mail.model.domain.MovementHistory;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.service.MailDepartmentServiceImpl;
import com.example.mail.model.service.MovementHistoryServiceImpl;
import com.example.mail.model.service.PostalItemServiceImpl;
import com.example.mail.model.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    PostalItemServiceImpl postalItemService;

    @Autowired
    MailDepartmentServiceImpl mailDepartmentService;

    @Autowired
    MovementHistoryServiceImpl movementHistoryService;

    @Autowired
    UserServiceImpl userService;

    private final ObjectMapper jsonFormatter = new ObjectMapper();

    @GetMapping ("/getUserPostalItems")
    public ResponseEntity<String> getUserPostalItems(Authentication authentication) throws JsonProcessingException {

        List<PostalItem> allUserPostalItems = postalItemService.getAllUserPostalItems(authentication.getName());

        if (allUserPostalItems.isEmpty()) {
            return  new ResponseEntity<>("User doesn't have any postal items", HttpStatus.OK);
        }

        return new ResponseEntity<>(jsonFormatter.writeValueAsString(allUserPostalItems), HttpStatus.OK);
    }
    @GetMapping ("/getUserPostalItemsMovementHistory")
    public ResponseEntity<String> getUserPostalItemsMovementHistory(Authentication authentication) throws JsonProcessingException {
        List<PostalItem> allUserPostalItems = postalItemService.getAllUserPostalItems(authentication.getName());

        List<List<MovementHistory>> allMovementHistories = new ArrayList<List<MovementHistory>>();
        for (PostalItem postalItem :
             allUserPostalItems) {
            ArrayList<MovementHistory> postalItemMovementHistory
                    = new ArrayList<>(postalItem.getMovementHistoryList());
            if (!postalItemMovementHistory.isEmpty()) {
                allMovementHistories.add(postalItemMovementHistory);
            }
        }

        return new ResponseEntity<>(jsonFormatter.writeValueAsString(allMovementHistories), HttpStatus.OK);
    }

    @PostMapping("/registryPostalItem")
    public ResponseEntity<String> registryPostalItem(@RequestBody String payload) throws IOException {
        // The payload parameter contains the JSON string from the request body
        try {
            PostalItem postalItem = jsonFormatter.readValue(payload, PostalItem.class);

            if (mailDepartmentService.getMailDepartmentByIndex(postalItem.getRecipientIndex()).isEmpty()) {
                return new ResponseEntity<>("Mail Department doesn't exists", HttpStatus.BAD_REQUEST);
            }

            if (!(userService.findUserByUsername(postalItem.getRecipientName())
                    && userService.findUserByUsername(postalItem.getSenderName()))) {
                return new ResponseEntity<>("Recipient user or Sender user don't exists",
                                HttpStatus.BAD_REQUEST);
            }

            MovementHistory movementHistory =
                    new MovementHistory(
                            postalItem.getMailDepartment(),
                            postalItem,
                            MovementType.CONSIDERATIONTOREGISTRY);
            postalItemService.save(postalItem);
            movementHistoryService.save(movementHistory);
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Postal item is on consideration to registry", HttpStatus.OK);
    }

    @GetMapping("/isUserExists")
    public ResponseEntity<Boolean> isUserExists(String username) {
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/makeOrderToTakePostalItem")
    public ResponseEntity<String> makeOrderToTakePostalItem(Authentication authentication, Long id) throws JsonProcessingException {
        Optional<PostalItem> postalItem = postalItemService.getPostalItemById(id);
        if (postalItem.isEmpty()) {
            return new ResponseEntity<>("Postal item doesn't exists", HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equals(postalItem.get().getRecipientName(), authentication.getName())) {

            return ResponseEntity.badRequest().body("You can't take someone else's Postal Item");
            /*
            return new ResponseEntity<>(jsonFormatter.writeValueAsString("You can't take someone else's Postal Item"),
                    HttpStatus.FORBIDDEN);
            */
        }


        MovementHistory movementHistory =
                new MovementHistory(
                        postalItem.get().getMailDepartment(),
                        postalItem.get(),
                        MovementType.CONSIDERATIONTOTAKE);
        movementHistoryService.save(movementHistory);

        return new ResponseEntity<>("Order successfully created", HttpStatus.OK);
    }

    /*
    @GetMapping ("/getUserOutgoingPostalItems")
    public ResponseEntity<String> getUserOutgoingPostalItems(Authentication authentication) throws JsonProcessingException {
        if (!authentication.isAuthenticated()) {
            return new ResponseEntity<>("You are not authorized", HttpStatus.FORBIDDEN);
        }
        List<PostalItem> postalItems = postalItemService.getPostalItemListBySenderName(authentication.getName());
        return new ResponseEntity<>(jsonFormater.writeValueAsString(postalItems), HttpStatus.OK);
    }

    @GetMapping("/getUserIncomingPostalItems")
    public ResponseEntity<String> getUserIncomingPostalItems(Authentication authentication) throws JsonProcessingException {
        if (!authentication.isAuthenticated()) {
            return new ResponseEntity<>("You are not authorized", HttpStatus.FORBIDDEN);
        }
        List<PostalItem> postalItems = postalItemService.getPostalItemListByRecipientName(authentication.getName());
        return new ResponseEntity<>(jsonFormater.writeValueAsString(postalItems), HttpStatus.OK);
    }
     */
}
