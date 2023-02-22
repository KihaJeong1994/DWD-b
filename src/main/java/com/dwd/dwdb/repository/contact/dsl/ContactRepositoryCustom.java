package com.dwd.dwdb.repository.contact.dsl;

import com.dwd.dwdb.model.contact.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepositoryCustom {
    Page<Contact> search(String title, String description, String createdBy, Pageable pageable);
}
