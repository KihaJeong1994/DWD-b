package com.dwd.dwdb.service;

import com.dwd.dwdb.model.LectureReview;
import com.dwd.dwdb.repository.LectureRepository;
import com.dwd.dwdb.repository.LectureReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureReviewServiceImpl implements LectureReviewService{

    private final LectureReviewRepository lectureReviewRepository;
    private final LectureRepository lectureRepository;
    @Override
    public Page<LectureReview> getLectureReviewByLectureId(String lectureId, Pageable pageable) {
        return lectureReviewRepository.findByLectureId(lectureId,pageable);
    }

    @Override
    public LectureReview insertLectureReview(LectureReview lectureReview) {
        LectureReview lectureReviewSaved = lectureReviewRepository.save(lectureReview);
        String lectureId = lectureReviewSaved.getLectureId();
        Double roundAvgRate = Math.round(lectureReviewRepository.getAvgRate(lectureId)*10D)/10D;
        int reviewsCnt = lectureReviewRepository.countByLectureId(lectureId);
        lectureRepository.findAndSetRateAndReviewsCntById(lectureId,roundAvgRate,reviewsCnt);
        return lectureReviewSaved;
    }
}
