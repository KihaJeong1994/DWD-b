package com.dwd.dwdb.service.lecture;

import com.dwd.dwdb.model.lecture.LectureReview;
import com.dwd.dwdb.repository.lecture.LectureRepository;
import com.dwd.dwdb.repository.lecture.LectureReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public LectureReview updateLectureReview(LectureReview reviewChanged) {
        Optional<LectureReview> optionalReview = lectureReviewRepository.findById(reviewChanged.getId());
        if(optionalReview.isPresent()){
            LectureReview review = optionalReview.get();
            review.setReview(reviewChanged.getReview());
            review.setRate(reviewChanged.getRate());
            LectureReview saved = lectureReviewRepository.save(review);
            int cnt = lectureReviewRepository.countByLectureId(review.getLectureId());
            Double avgRate = lectureReviewRepository.getAvgRate(review.getLectureId());
            lectureRepository.findAndSetRateAndReviewsCntById(review.getLectureId(),avgRate,cnt);
            return saved;
        }else {
            throw new IllegalArgumentException("LectureReview with id:"+reviewChanged.getId()+" is not found");
        }
    }

    @Override
    public void deleteLectureReviewById(String id) {
        Optional<LectureReview> reviewToDelete = lectureReviewRepository.findById(id);
        if(reviewToDelete.isPresent()){
            String lectureId = reviewToDelete.get().getLectureId();
            lectureReviewRepository.deleteById(id);
            int cnt = lectureReviewRepository.countByLectureId(lectureId);
            Double avgRate = lectureReviewRepository.getAvgRate(lectureId);
            if(avgRate==null) avgRate=0.0;
            lectureRepository.findAndSetRateAndReviewsCntById(lectureId,avgRate,cnt);
        }else {
            throw new IllegalArgumentException("LectureReview with id:"+id+" is not found");
        }
    }
}
