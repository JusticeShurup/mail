package com.example.mail.model.service;

import com.example.mail.model.Enum.MovementType;
import com.example.mail.model.domain.MailDepartment;
import com.example.mail.model.domain.MovementHistory;
import com.example.mail.model.domain.PostalItem;
import com.example.mail.model.repository.MailDepartmentRepository;
import com.example.mail.model.repository.PostalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostalItemServiceImpl implements PostalItemService {

    @Autowired
    PostalItemRepository postalItemRepository;
    @Autowired
    MailDepartmentRepository mailDepartmentRepository;
    @Override
    public void save(PostalItem postalItem) {
        postalItemRepository.save(postalItem);
    }

    @Override
    public Optional<PostalItem> getPostalItemById(long id) {
        return postalItemRepository.findById(id);
    }

    @Override
    public List<PostalItem> getPostalItemList() {
        return postalItemRepository.findAll();
    }

    @Override
    public List<PostalItem> getAllUserPostalItems(String username) {
        return postalItemRepository.findPostalItemByRecipientNameOrSenderName(username, username);
    }

    @Override
    public List<PostalItem> getPostalItemListByRecipientName(String recipientUsername) {
        return postalItemRepository.findPostalItemByRecipientName(recipientUsername);
    }

    @Override
    public List<PostalItem> getPostalItemListBySenderName(String senderUsername) {
        return postalItemRepository.findPostalItemBySenderName(senderUsername);
    }

    @Override
    public List<PostalItem> getPostalItemListByMailDepartmentId(Long id) {
        Optional<MailDepartment> mailDepartment = mailDepartmentRepository.findById(id);
        if (mailDepartment.isEmpty()) return new ArrayList<>(){};

        return postalItemRepository.findPostalItemByMailDepartment(mailDepartment.get());
    }

    @Override
    public List<PostalItem> getConsiderationToRegistryPostalItemListByMailDepartmentId(Long id) {
        ArrayList<PostalItem> postalItems = (ArrayList<PostalItem>) getPostalItemListByMailDepartmentId(id);

        ArrayList<PostalItem> selectedPostalItems = new ArrayList<>();

        for (var postalItem :
             postalItems) {
            if (postalItem.getMovementHistoryList().get(0).getMovementType() == MovementType.CONSIDERATIONTOREGISTRY
                    && postalItem.getMovementHistoryList().size() == 1) {
                selectedPostalItems.add(postalItem);
            }
        }
        return selectedPostalItems;
    }

    @Override
    public List<PostalItem> getConsiderationToTakePostalItemListByMailDepartmentId(long id) {
        ArrayList<PostalItem> postalItems = (ArrayList<PostalItem>) getPostalItemListByMailDepartmentId(id);

        ArrayList<PostalItem> selectedPostalItems = new ArrayList<>();

        for (var postalItem :
                postalItems) {
            if (postalItem.getCurrentMovementHistory().getMovementType() == MovementType.CONSIDERATIONTOTAKE) {
                selectedPostalItems.add(postalItem);
            }
        }
        return selectedPostalItems;
    }


}
