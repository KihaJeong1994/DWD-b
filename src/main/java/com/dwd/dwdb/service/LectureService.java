package com.dwd.dwdb.service;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    Page<Lecture> searchLecture(String title, Site site, Double rate, Pageable pageable);

    Lecture insertLecture(Lecture lecture);

    Optional<Lecture> getLectureById(String id);
}
