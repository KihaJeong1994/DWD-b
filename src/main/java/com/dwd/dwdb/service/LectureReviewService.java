package com.dwd.dwdb.service;

import com.dwd.dwdb.model.LectureReview;

import java.util.List;

public interface LectureReviewService {
    List<LectureReview> getLectureReviewByLectureId(String lectureId);

    LectureReview insertLectureReview(LectureReview lectureReview);
}
