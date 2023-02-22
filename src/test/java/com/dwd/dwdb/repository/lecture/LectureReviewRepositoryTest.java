package com.dwd.dwdb.repository.lecture;

import com.dwd.dwdb.model.lecture.LectureReview;
import com.dwd.dwdb.repository.lecture.LectureReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            new LectureReview(null,"1","너무너무 좋은 강의입니다!","Flutter꿈나무",5)
            ,new LectureReview("gcaewor","1","어서 다음 강의 출시 해주세요~","스프링master",4.5)
            ,new LectureReview(null,"2","별로인듯;;","노잼충",2.5)
            ,new LectureReview(null,"3","짱짱맨;;","obejcetc",4.0)
            ,new LectureReview(null,"3","별로인듯;;","노잼충",2.5)
    );

    @BeforeEach
    void setUp(){
        lectureReviewRepository.saveAll(dataSets.subList(0,1));
        lectureReviewRepository.saveAll(dataSets.subList(1,dataSets.size()));
    }

    @AfterEach
    void cleanUp(){
        lectureReviewRepository.deleteAll();
    }

    @Test
    void should_save_review_and_return_review(){
        LectureReview review = lectureReviewRepository.save(new LectureReview(null, "2", "thank you so much for great class", "DavidC", 5));
        assertNotNull(review.getId());
        assertNotNull(review.getCreatedAt());
        assertEquals("2",review.getLectureId());
        assertEquals("thank you so much for great class",review.getReview());
        assertEquals("DavidC",review.getCreatedBy());
        assertEquals(5,review.getRate());
    }

    @Test
    void should_return_review_with_lectureId_1(){
        Pageable pageable = PageRequest.of(0,20, Sort.by(Sort.Direction.DESC,"updatedAt"));
        Page<LectureReview> reviews = lectureReviewRepository.findByLectureId("1",pageable);
        assertEquals(2,reviews.getContent().size());
        for(var r : reviews){
            System.out.println(r.getUpdatedAt());
        }
        assertTrue(reviews.getContent().get(0).getUpdatedAt().compareTo(reviews.getContent().get(1).getUpdatedAt())>0);
    }

    @Test
    void should_return_average_of_rate_where_id_1(){
        String lectureId = "1";
        Double avg = lectureReviewRepository.getAvgRate(lectureId);
        assertEquals((5.0+4.5)/2.0,avg);
    }

    @Test
    void should_return_0_for_average_of_rate_where_id_4(){
        String lectureId = "4";
        Double avg = lectureReviewRepository.getAvgRate(lectureId);
        assertEquals(null,avg);
    }

    @Test
    void should_return_count_where_id_1(){
        String lectureId = "1";
        int cnt = lectureReviewRepository.countByLectureId(lectureId);
        assertEquals(2,cnt);
    }

    @Test
    void should_set_review_as_follow(){
        LectureReview reviewChanged = new LectureReview("gcaewor", "1", "다시 생각해보니 들을만 한듯?", "스프링master", 5.0);
        Optional<LectureReview> optionalReview = lectureReviewRepository.findById(reviewChanged.getId());
        Instant updatedAtBefore = optionalReview.get().getUpdatedAt();
        assertTrue(optionalReview.isPresent());
        if(optionalReview.isPresent()){
            LectureReview review = optionalReview.get();
            review.setReview(reviewChanged.getReview());
            review.setRate(reviewChanged.getRate());
            LectureReview save = lectureReviewRepository.save(review);
            assertNotNull(save);
        }
        LectureReview saved = lectureReviewRepository.findById(reviewChanged.getId()).get();
        assertEquals(reviewChanged.getReview(),saved.getReview());
        assertEquals(reviewChanged.getRate(),saved.getRate());
        assertTrue(saved.getUpdatedAt().isAfter(updatedAtBefore));
    }


}
