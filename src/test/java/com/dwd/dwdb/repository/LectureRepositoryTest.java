package com.dwd.dwdb.repository;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.lecture.Lecture;
import com.dwd.dwdb.repository.lecture.LectureRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            new Lecture("1","스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술", Site.inflearn,4.3,"lecture1.png","abc.com","very good")
            ,new Lecture(null,"10개 프로젝트로 완성하는 백엔드 웹개발(Java/Spring)", Site.fastcampus,3.5,"lecture2.png","abc.com","very good")
            ,new Lecture(null,"[코드팩토리] [입문] Dart 언어 4시간만에 완전정복", Site.inflearn,5.0,"lecture3.png","abc.com","very good")
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
    void should_save_and_return_lecture(){
        Lecture lecture = lectureRepository.save(new Lecture(null, "30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png","abc.com","very good"));
        assertNotNull(lecture.getId());
        assertEquals("30개 프로젝트로 배우는 프론트엔드 with React",lecture.getTitle());
        assertEquals(Site.fastcampus,lecture.getSite());
        assertEquals(2.9,lecture.getRate());
        assertNotNull(lecture.getCreatedAt());
    }

    @Test
    void should_return_all(){
        List<Lecture> lectures = lectureRepository.findAll();
        assertEquals(lectures.size(), dataSets.size());
    }

    @Test
    void should_return_lecture_with_title_containing_스프링(){
        String title = "스프링";
        Site site = null;
        Double rate = null;
        Pageable pageable = PageRequest.of(0,20);
        Page<Lecture> lectures = lectureRepository.search(title, site, rate,pageable);
        assertEquals(1,lectures.getNumberOfElements());
    }

    @Test
    void should_return_lecture_with_site_inflearn_and_rate_goe_4_and_lt_5(){
        String title =  null;
        Site site = Site.inflearn;
        Double rate = 4.0;
        Pageable pageable = PageRequest.of(0,20);
        Page<Lecture> lectures = lectureRepository.search(title, site, rate, pageable);
        assertEquals(1,lectures.getNumberOfElements());
    }

    @Test
    void should_return_lecture_with_id_a(){
        Lecture lecture = lectureRepository.save(new Lecture( null,"30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png","abc.com","very good"));
        assertNotNull(lecture.getId());
        Optional<Lecture> lectureWithId = lectureRepository.findById(lecture.getId());
        assertTrue(lectureWithId.isPresent());
        assertEquals("30개 프로젝트로 배우는 프론트엔드 with React",lectureWithId.get().getTitle());
    }

    @Test
    void should_update_rate_by_3(){
        lectureRepository.findAndSetRateAndReviewsCntById("1",4.0,2);
        Optional<Lecture> lecture = lectureRepository.findById("1");
        assertEquals(4.0, lecture.get().getRate());
        assertEquals(2, lecture.get().getReviewsCnt());
    }


}
