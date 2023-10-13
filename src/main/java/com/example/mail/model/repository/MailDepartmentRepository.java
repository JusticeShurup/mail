package com.example.mail.model.repository;
import org.springframework.stereotype.Repository;

import com.example.mail.model.domain.MailDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MailDepartmentRepository extends JpaRepository<MailDepartment, Long> {

    default List<MailDepartment> getMailDepartmentList() {
        return findAll();
    }

    /*
    List<PostalItem> findAllByMailDepartment(MailDepartment department);
    * */


}
