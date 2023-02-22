package com.dwd.dwdb.service.contact;

import com.dwd.dwdb.model.contact.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContactService {
    Page<Contact> search(String title, String description, String createdBy, Pageable pageable);
    Contact insertContact(Contact contact);

    Contact updateContact(Contact contact);

    void deleteContactById(String id);

    Optional<Contact> getContactById(String id);
}
