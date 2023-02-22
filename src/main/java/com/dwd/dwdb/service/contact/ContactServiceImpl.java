package com.dwd.dwdb.service.contact;

import com.dwd.dwdb.model.contact.Contact;
import com.dwd.dwdb.repository.contact.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{
    private final ContactRepository contactRepository;


    @Override
    public Page<Contact> search(String title, String description, String createdBy, Pageable pageable) {
        return contactRepository.search(title,description,createdBy,pageable);
    }

    @Override
    public Contact insertContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Contact contactChanged) {
        Optional<Contact> contactOptional = contactRepository.findById(contactChanged.getId());
        if(contactOptional.isPresent()){
            Contact contact = contactOptional.get();
            contact.setTitle(contactChanged.getTitle());
            contact.setDescription(contactChanged.getDescription());
            return contactRepository.save(contact);
        }else {
            throw new IllegalArgumentException("LectureReview with id:"+contactChanged.getId()+" is not found");
        }
    }

    @Override
    public void deleteContactById(String id) {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if(contactOptional.isPresent()){
            contactRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException("LectureReview with id:"+id+" is not found");
        }
    }

    @Override
    public Optional<Contact> getContactById(String id) {
        return contactRepository.findById(id);
    }
}
