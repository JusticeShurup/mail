package com.example.mail.model.repository;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface PostalItemRepository extends JpaRepository<PostalItem, Long> {

    ArrayList<PostalItem> findPostalItemByRecipientNameOrSenderName(String recipientName, String senderName);
    ArrayList<PostalItem> findPostalItemByRecipientName(String recipientName);

    ArrayList<PostalItem> findPostalItemByMailDepartment(MailDepartment mailDepartment);

    ArrayList<PostalItem> findPostalItemBySenderName(String senderName);
}

