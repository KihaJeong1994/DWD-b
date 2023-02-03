package com.dwd.dwdb.service;

import com.dwd.dwdb.model.LectureReview;
import com.dwd.dwdb.repository.LectureRepository;
import com.dwd.dwdb.repository.LectureReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureReviewServiceImpl implements LectureReviewService{

    private final LectureReviewRepository lectureReviewRepository;
    private final LectureRepository lectureRepository;
    @Override
    public List<LectureReview> getLectureReviewByLectureId(String lectureId) {
        return lectureReviewRepository.findByLectureIdOrderByUpdatedAtDesc(lectureId);
    }

    @Override
    public LectureReview insertLectureReview(LectureReview lectureReview) {
        LectureReview lectureReviewSaved = lectureReviewRepository.save(lectureReview);
        String lectureId = lectureReviewSaved.getLectureId();
        Double roundAvgRate = Math.round(lectureReviewRepository.getAvgRate(lectureId)*10D)/10D;
        lectureRepository.findAndSetRateById(lectureId,roundAvgRate);
        return lectureReviewSaved;
    }
}
