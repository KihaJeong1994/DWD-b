package com.dwd.dwdb.repository;

import com.dwd.dwdb.model.LectureReview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LectureReviewRepository extends MongoRepository<LectureReview,String> {
    List<LectureReview> findByLectureIdOrderByUpdatedAtDesc(String lectureId);
}
