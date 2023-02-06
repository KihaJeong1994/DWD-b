package com.dwd.dwdb.repository;

import com.dwd.dwdb.model.LectureReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LectureReviewRepository extends MongoRepository<LectureReview,String>  {
    Page<LectureReview> findByLectureId(String lectureId, Pageable pageable);

    @Aggregation(pipeline = {
            "{$match:{lectureId:?0}}"
            ," {$group:{_id:null, avg:{$avg:'$rate'}}}"
    })
    Double getAvgRate(String lectureId);

}
