package com.dwd.dwdb.service;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.exception.ResourceNotFoundException;
import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.repository.LectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureServiceImpl lectureService;

    @Test
    void should_return_lecture(){
        when(lectureRepository.save(new Lecture(null,"30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png","abc.com","very good")))
                .thenReturn(new Lecture(null,"30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png","abc.com","very good"));
        Lecture lecture = lectureService.insertLecture(new Lecture(null,"30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png","abc.com","very good"));
        assertNotNull(lecture);
    }

    @Test
    void should_return_lecture_list(){
        String title =  null;
        Site site = Site.inflearn;
        Double rate = 4.0;
        when(lectureRepository.search(title,site,rate)).thenReturn(Arrays.asList(
                new Lecture("1","스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술", Site.inflearn,4.3,"lecture1.png","abc.com","very good")
        ));
        List<Lecture> lectures = lectureService.searchLecture(title, site, rate);
        assertNotNull(lectures);
    }

    @Test
    void should_return_lecture_with_id_1(){
        Lecture lecture = new Lecture("1","30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png","abc.com","very good");
        String id = lecture.getId();
        when(lectureRepository.findById(id)).thenReturn(Optional.of(lecture));
        Optional<Lecture> lectureById = lectureService.getLectureById(id);
        assertTrue(lectureById.isPresent());
    }

    @Test
    void should_throw_ResourceNotFoundException(){
        String id = "2";
        when(lectureRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->lectureService.getLectureById(id));

    }

}
