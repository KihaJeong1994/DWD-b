package com.dwd.dwdb.repository.lecture;

import com.dwd.dwdb.model.lecture.LectureReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LectureReviewRepository extends MongoRepository<LectureReview,String>  {
    Page<LectureReview> findByLectureId(String lectureId, Pageable pageable);

    @Aggregation(pipeline = {
            "{$match:{lectureId:?0}}"
            ," {$group:{_id:null, avg:{$avg:'$rate'}}}"
    })
    Double getAvgRate(String lectureId);

    int countByLectureId(String lectureId);
}
