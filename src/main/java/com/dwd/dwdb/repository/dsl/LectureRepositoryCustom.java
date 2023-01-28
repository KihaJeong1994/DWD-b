package com.dwd.dwdb.repository.dsl;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;

import java.util.List;

public interface LectureRepositoryCustom {
    List<Lecture> search(String title, Site site, Double rate);
}
