package com.dwd.dwdb.repository;

import com.dwd.dwdb.model.LectureReview;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@ExtendWith(SpringExtension.class)
public class LectureReviewRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    @Autowired
    private LectureReviewRepository lectureReviewRepository;


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    private List<LectureReview> dataSets = Arrays.asList(
            new LectureReview("1","너무너무 좋은 강의입니다!","Flutter꿈나무",5)
            ,new LectureReview("1","어서 다음 강의 출시 해주세요~","스프링master",4.5)
            ,new LectureReview("2","별로인듯;;","노잼충",2.5)
    );

    @BeforeEach
    void setUp(){
        lectureReviewRepository.saveAll(dataSets);
    }

    @AfterEach
    void cleanUp(){
        lectureReviewRepository.deleteAll();
    }

    @Test
    void should_return_review_with_lectureId_1(){
        List<LectureReview> reviews = lectureReviewRepository.findByLectureId("1");
        assertEquals(2,reviews.size());
    }
}
