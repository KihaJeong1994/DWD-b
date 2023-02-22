package com.dwd.dwdb.service.lecture;

import com.dwd.dwdb.model.lecture.LectureReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureReviewService {
    Page<LectureReview> getLectureReviewByLectureId(String lectureId, Pageable pageable);

    LectureReview insertLectureReview(LectureReview lectureReview);

    LectureReview updateLectureReview(LectureReview lectureReview);

    void deleteLectureReviewById(String id);
}
