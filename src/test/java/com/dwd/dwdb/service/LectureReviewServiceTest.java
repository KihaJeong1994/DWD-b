package com.dwd.dwdb.service;

import com.dwd.dwdb.model.lecture.LectureReview;
import com.dwd.dwdb.repository.lecture.LectureRepository;
import com.dwd.dwdb.repository.lecture.LectureReviewRepository;
import com.dwd.dwdb.service.lecture.LectureReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
        String lectureId = "1";
        LectureReview review = new LectureReview(null, lectureId, "너무너무 좋은 강의입니다!", "Flutter꿈나무", 5);
        when(lectureReviewRepository.save(review)).thenReturn(review);
        when(lectureReviewRepository.getAvgRate(lectureId)).thenReturn(4.75);
        when(lectureReviewRepository.countByLectureId(lectureId)).thenReturn(2);
        LectureReview lectureReview = lectureReviewService.insertLectureReview(review);
        assertNotNull(lectureReview);
        verify(lectureRepository).findAndSetRateAndReviewsCntById(lectureId,4.8,2);

    }

    @Test
    void should_return_review_with_lectureId_1(){
        String lectureId = "1";
        Pageable pageable = PageRequest.of(0,20);
        Page<LectureReview> expectedLectures = new PageImpl<>(Arrays.asList(
                new LectureReview("gaec","1","너무너무 좋은 강의입니다!","Flutter꿈나무",5)
                ,new LectureReview("etecae","1","어서 다음 강의 출시 해주세요~","스프링master",4.5)
        ));
        when(lectureReviewRepository.findByLectureId(lectureId,pageable)).thenReturn(expectedLectures);
        Page<LectureReview> reviews = lectureReviewService.getLectureReviewByLectureId(lectureId,pageable);
        for (var r : reviews){
            assertEquals("1",r.getLectureId());
        }
    }

    @Test
    void should_update_review_and_lecture_rate(){
        String lectureId = "1";
        LectureReview review = new LectureReview("tacera", lectureId, "너무너무 좋은 강의입니다!", "Flutter꿈나무", 5);
        when(lectureReviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        when(lectureReviewRepository.save(review)).thenReturn(review);
        when(lectureReviewRepository.getAvgRate(lectureId)).thenReturn(4.5);
        when(lectureReviewRepository.countByLectureId(lectureId)).thenReturn(2);
        LectureReview lectureReview = lectureReviewService.updateLectureReview(review);
        verify(lectureRepository).findAndSetRateAndReviewsCntById(lectureId,4.5,2);
        assertNotNull(lectureReview);

    }

    @Test
    void should_throw_illegalArgumentException_when_no_review(){
        String lectureId = "1";
        LectureReview review = new LectureReview("tacera", lectureId, "너무너무 좋은 강의입니다!", "Flutter꿈나무", 5);
        when(lectureReviewRepository.findById(review.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,()->lectureReviewService.updateLectureReview(review));
    }

    @Test
    void should_delete_review_with_id_tacera_and_change_lecture_avgRate_and_reviewsCnt(){
        String reviewId = "tacera";
        String lectureId = "1";
        LectureReview review = new LectureReview(reviewId, lectureId, "너무너무 좋은 강의입니다!", "Flutter꿈나무", 5);
        when(lectureReviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(lectureReviewRepository.getAvgRate(lectureId)).thenReturn(4.5);
        when(lectureReviewRepository.countByLectureId(lectureId)).thenReturn(2);
        lectureReviewService.deleteLectureReviewById(reviewId);
        verify(lectureRepository).findAndSetRateAndReviewsCntById(lectureId,4.5,2);
    }

}
