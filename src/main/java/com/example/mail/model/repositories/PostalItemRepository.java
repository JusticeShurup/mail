package com.example.mail.model.repositories;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostalItemRepository extends JpaRepository<PostalItem, Long> {
}

