package com.dwd.dwdb.service;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    List<Lecture> searchLecture(String title, Site site, Double rate);

    Lecture insertLecture(Lecture lecture);

    Optional<Lecture> getLectureById(String id);
}
