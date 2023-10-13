package com.example.mail.model.service;

import com.example.mail.model.domain.PostalItem;

import java.util.List;
import java.util.Optional;

public interface PostalItemService {
    void save(PostalItem postalItem);
    Optional<PostalItem> getPostalItemById(long id);
    List<PostalItem> getPostalItemList();
}
