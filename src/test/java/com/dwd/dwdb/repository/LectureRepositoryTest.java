package com.dwd.dwdb.repository;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;
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
public class LectureRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private LectureRepository lectureRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    private List<Lecture> dataSets = Arrays.asList(
            new Lecture(null,"스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술", Site.inflearn,4.3,"lecture1.png")
            ,new Lecture(null,"10개 프로젝트로 완성하는 백엔드 웹개발(Java/Spring)", Site.fastcampus,3.5,"lecture2.png")
            ,new Lecture(null,"[코드팩토리] [입문] Dart 언어 4시간만에 완전정복", Site.inflearn,5.0,"lecture3.png")
    );

    @BeforeEach
    void setUp(){
        lectureRepository.saveAll(dataSets);
    }

    @AfterEach
    void cleanUp(){
        lectureRepository.deleteAll();
    }

    @Test
    void should_return_all(){
        List<Lecture> lectures = lectureRepository.findAll();
        assertTrue(lectures.size()==dataSets.size());
    }

    @Test
    void should_return_lecture_with_title_containing_스프링(){
        String title = "스프링";
        Site site = null;
        Double rate = null;
        List<Lecture> lectures = lectureRepository.search(title, site, rate);
        assertEquals(1,lectures.size());
    }

    @Test
    void should_return_lecture_with_site_inflearn_and_rate_goe_4_and_lt_5(){
        String title =  null;
        Site site = Site.inflearn;
        Double rate = 4.0;
        List<Lecture> lectures = lectureRepository.search(title, site, rate);
        assertEquals(1,lectures.size());
    }
}
