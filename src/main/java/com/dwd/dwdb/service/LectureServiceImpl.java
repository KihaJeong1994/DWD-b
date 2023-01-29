package com.dwd.dwdb.service;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService{
    private final LectureRepository lectureRepository;

    @Override
    public List<Lecture> searchLecture(String title, Site site, Double rate) {
        return lectureRepository.search(title,site,rate);
    }

    @Override
    public Lecture insertLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }
}
