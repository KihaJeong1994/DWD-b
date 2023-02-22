package com.dwd.dwdb.repository.contact;

import com.dwd.dwdb.model.contact.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@DataMongoTest
@Testcontainers
@ExtendWith(SpringExtension.class)
public class ContactRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private ContactRepository contactRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    private List<Contact> dataSets = Arrays.asList(
        new Contact("1","니콜라스 강의도 추가해주세요","정말 좋은 강의들이 많습니다.","니콜라스팬", Instant.now(),Instant.now())
        ,new Contact("2","아무 글이나 써봄","ㅋㅋㄹㅃㅃ","허허허", Instant.now(),Instant.now())
        ,new Contact("3","다른 컨텐츠 추가 부탁","컨텐츠가 한정적이네.","비수맨", Instant.now(),Instant.now())
    );

    @BeforeEach
    void setUp(){
        contactRepository.saveAll(dataSets);
    }

    @AfterEach
    void cleanUp(){
        contactRepository.deleteAll();
    }

    @Test
    void should_return_all_without_any_param(){
        String title = null;
        String description = null;
        String createdBy = null;
        Pageable pageable = PageRequest.of(0,20);
        Page<Contact> contacts = contactRepository.search(title, description, createdBy, pageable);
        assertEquals(dataSets.size(),contacts.getNumberOfElements());
    }

    @Test
    void should_return_contact_with_title_니콜라스(){
        String title = "니콜라스";
        String description = null;
        String createdBy = null;
        Pageable pageable = PageRequest.of(0,20);
        Page<Contact> contacts = contactRepository.search(title, description, createdBy, pageable);
        assertEquals(1,contacts.getNumberOfElements());
        assertTrue(contacts.getContent().get(0).getTitle().contains(title));
    }
}
