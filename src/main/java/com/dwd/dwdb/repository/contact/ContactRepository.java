package com.dwd.dwdb.repository.contact;

import com.dwd.dwdb.model.contact.Contact;
import com.dwd.dwdb.repository.contact.dsl.ContactRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> , ContactRepositoryCustom {
}
