package com.example.mail.model.repository;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.MovementHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementHistoryRepository extends JpaRepository<MovementHistory, Long> {

}
