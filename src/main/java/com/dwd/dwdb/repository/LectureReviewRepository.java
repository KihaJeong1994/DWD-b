package com.dwd.dwdb.repository;

import com.dwd.dwdb.model.LectureReview;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LectureReviewRepository extends MongoRepository<LectureReview,String>  {
    List<LectureReview> findByLectureIdOrderByUpdatedAtDesc(String lectureId);

    @Aggregation(pipeline = {
            "{$match:{lectureId:?0}}"
            ," {$group:{_id:null, avg:{$avg:'$rate'}}}"
    })
    Double getAvgRate(String lectureId);

}
