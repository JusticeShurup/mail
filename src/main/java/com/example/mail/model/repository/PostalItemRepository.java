package com.example.mail.model.repository;

import com.example.mail.model.domain.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PostalItemRepository extends JpaRepository<PostalItem, Long> {
    ArrayList<PostalItem> findPostalItemByRecipientName(String recipientName);
}

