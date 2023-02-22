package com.dwd.dwdb.service.lecture;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.exception.ResourceNotFoundException;
import com.dwd.dwdb.model.lecture.Lecture;
import com.dwd.dwdb.repository.lecture.LectureRepository;
import com.dwd.dwdb.service.lecture.LectureServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

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
        Page<Lecture> pro= Mockito.mock(Page.class);
        Pageable pageable = PageRequest.of(0,20);
        when(lectureRepository.search(title,site,rate,pageable)).thenReturn(pro);
        Page<Lecture> lectures = lectureService.searchLecture(title, site, rate,pageable);
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
