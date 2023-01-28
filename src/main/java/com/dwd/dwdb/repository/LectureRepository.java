package com.dwd.dwdb.repository;

import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.repository.dsl.LectureRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LectureRepository extends MongoRepository<Lecture,String> , LectureRepositoryCustom {

}
