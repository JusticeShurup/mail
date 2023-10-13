package com.example.mail.model.repository;

import com.example.mail.model.domain.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostalItemRepository extends JpaRepository<PostalItem, Long> {
}

