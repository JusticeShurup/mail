package com.example.mail.model.service;

import com.example.mail.model.domain.PostalItem;

import java.util.List;
import java.util.Optional;

public interface PostalItemService {
    void save(PostalItem postalItem);
    Optional<PostalItem> getPostalItemById(long id);
    List<PostalItem> getPostalItemList();


    // Returns all user postal items (Outgoing and incoming)
    List<PostalItem> getAllUserPostalItems(String username);
    List<PostalItem> getPostalItemListByRecipientName(String recipientUsername);
    List<PostalItem> getPostalItemListBySenderName(String senderUsername);

    List<PostalItem> getPostalItemListByMailDepartmentId(Long id);

    List<PostalItem>  getConsiderationToRegistryPostalItemListByMailDepartmentId(Long id);
}
