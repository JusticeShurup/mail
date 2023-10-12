package com.example.mail.model.services;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.PostalItem;

import java.util.List;

public interface PostalItemService {
    void save(PostalItem postalItem);
    List<PostalItem> getPostalItemList();
    void registryPostalItem(String postalType, String recipientIndex, String recipientAddress, String recipientName, long mailDepartmentId, boolean isDelivered);
}
