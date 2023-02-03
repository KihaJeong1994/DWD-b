package com.dwd.dwdb.service;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.exception.ResourceNotFoundException;
import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService{
    private final LectureRepository lectureRepository;

    @Override
    public Page<Lecture> searchLecture(String title, Site site, Double rate, Pageable pageable) {
        return lectureRepository.search(title,site,rate,pageable);
    }

    @Override
    public Lecture insertLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @Override
    public Optional<Lecture> getLectureById(String id) {
        Optional<Lecture> lecture = lectureRepository.findById(id);
        if(!lecture.isPresent()){
            throw new ResourceNotFoundException("Lecture with id '"+id+"' not found");
        }
        return lecture;
    }
}
