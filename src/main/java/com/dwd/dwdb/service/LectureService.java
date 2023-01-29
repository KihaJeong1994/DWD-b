package com.dwd.dwdb.service;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;

import java.util.List;

public interface LectureService {
    List<Lecture> searchLecture(String title, Site site, Double rate);

    Lecture insertLecture(Lecture lecture);
}
