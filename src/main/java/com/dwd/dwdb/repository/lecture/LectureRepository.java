package com.dwd.dwdb.repository.lecture;

import com.dwd.dwdb.model.lecture.Lecture;
import com.dwd.dwdb.repository.lecture.dsl.LectureRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

public interface LectureRepository extends MongoRepository<Lecture,String> , LectureRepositoryCustom {

    @Update("{$set:{'rate':?1,'reviewsCnt':?2}}")
    void findAndSetRateAndReviewsCntById(String id, Double rate, int reviewsCnt);
}
