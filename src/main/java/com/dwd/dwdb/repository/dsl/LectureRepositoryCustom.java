package com.dwd.dwdb.repository.dsl;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.lecture.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureRepositoryCustom {
    Page<Lecture> search(String title, Site site, Double rate, Pageable pageable);
}
