package com.dwd.dwdb.service;

import com.dwd.dwdb.model.LectureReview;
import com.dwd.dwdb.repository.LectureReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureReviewServiceImpl implements LectureReviewService{

    private final LectureReviewRepository lectureReviewRepository;
    @Override
    public List<LectureReview> getLectureReviewByLectureId(String lectureId) {
        return lectureReviewRepository.findByLectureIdOrderByUpdatedAtDesc(lectureId);
    }

    @Override
    public LectureReview insertLectureReview(LectureReview lectureReview) {
        return lectureReviewRepository.save(lectureReview);
    }
}
