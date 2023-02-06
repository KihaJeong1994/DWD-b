package com.dwd.dwdb.service;

import com.dwd.dwdb.model.LectureReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LectureReviewService {
    Page<LectureReview> getLectureReviewByLectureId(String lectureId, Pageable pageable);

    LectureReview insertLectureReview(LectureReview lectureReview);
}
