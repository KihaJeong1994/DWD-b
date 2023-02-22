package com.dwd.dwdb.controller.contact;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.exception.ResourceNotFoundException;
import com.dwd.dwdb.model.contact.Contact;
import com.dwd.dwdb.service.contact.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@CrossOrigin
public class ContactController {
    private final ContactService contactService;

    @GetMapping("")
    ResponseEntity<Page<Contact>> searchContacts(@RequestParam(required = false) String title
            , @RequestParam(required = false) String description
            , @RequestParam(required = false) String createdBy
            , Pageable pageable){
        Page<Contact> contacts = contactService.search(title, description, createdBy,pageable);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Contact> getContactById(@PathVariable("id") String id){
        try {
            Optional<Contact> contact = contactService.getContactById(id);
            return new ResponseEntity<>(contact.get(),HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    ResponseEntity<Contact> insertContact(@RequestBody Contact contact){
        return new ResponseEntity<>(contactService.insertContact(contact), HttpStatus.CREATED);
    }

    @PutMapping("")
    ResponseEntity<Contact> updateContact(@RequestBody Contact contact){
        try {
            return new ResponseEntity<>(contactService.updateContact(contact),HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteContactReviewById(@RequestParam String id){
        try {
            contactService.deleteContactById(id);
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
