package com.example.mail.model.services;

import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.repositories.MailDepartmentRepository;
import com.example.mail.model.repositories.PostalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostalItemServiceImpl implements PostalItemService{

    @Autowired
    PostalItemRepository postalItemRepository;
    @Autowired
    MailDepartmentRepository mailDepartmentRepository;
    @Override
    public void save(PostalItem postalItem) {
        postalItemRepository.save(postalItem);
    }

    @Override
    public List<PostalItem> getPostalItemList() {
        return postalItemRepository.findAll();
    }

    @Override
    public void registryPostalItem(String postalType, String recipientIndex, String recipientAddress, String recipientName, long mailDepartmentId, boolean isDelivered) {
        try {
            Optional<MailDepartment> mailDepartment = mailDepartmentRepository.findById(mailDepartmentId);
            PostalItem postalItem = new PostalItem(postalType, recipientIndex, recipientAddress, recipientName, mailDepartment.get(), isDelivered);
            postalItemRepository.save(postalItem);
        } catch (Exception exception)
        {
            System.out.println(exception.toString());
        }
    }

}
