package com.dwd.dwdb.service;

import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.model.LectureReview;
import com.dwd.dwdb.repository.LectureRepository;
import com.dwd.dwdb.repository.LectureReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LectureReviewServiceTest {

    @Mock
    private LectureReviewRepository lectureReviewRepository;

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureReviewServiceImpl lectureReviewService;

    @Test
    void should_save_and_return_review(){
        LectureReview review = new LectureReview(null, "1", "너무너무 좋은 강의입니다!", "Flutter꿈나무", 5);
        when(lectureReviewRepository.save(review)).thenReturn(review);
        LectureReview lectureReview = lectureReviewService.insertLectureReview(review);
        assertNotNull(lectureReview);
        // TODO : lecture에 저장되어있는 rate 값 갱신 확인 + 갱신 로직

    }

    @Test
    void should_return_review_with_lectureId_1(){
        String lectureId = "1";
        when(lectureReviewRepository.findByLectureIdOrderByUpdatedAtDesc(lectureId)).thenReturn(Arrays.asList(
                new LectureReview("gaec","1","너무너무 좋은 강의입니다!","Flutter꿈나무",5)
                ,new LectureReview("etecae","1","어서 다음 강의 출시 해주세요~","스프링master",4.5)
        ));
        List<LectureReview> reviews = lectureReviewService.getLectureReviewByLectureId(lectureId);
        for (var r : reviews){
            assertEquals("1",r.getLectureId());
        }
    }

}
