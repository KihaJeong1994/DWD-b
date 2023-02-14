package com.dwd.dwdb.controller;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.exception.ResourceNotFoundException;
import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.service.LectureService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
//@WebMvcTest(LectureController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Disabled// TODO : test without spring security
public class LectureContollerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LectureService lectureService;

    @Test
    void should_return_lecture_list() throws Exception {
        Page<Lecture> expectedLectures = new PageImpl<>(Collections.emptyList());
        Pageable pageable = PageRequest.of(0,20);
        when(lectureService.searchLecture(null, Site.inflearn,4.0,pageable)).thenReturn(expectedLectures);
        mockMvc.perform(get("/lecture?site=inflearn&rate=4.0"))
                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("스프링")));
                .andDo(print());

    }

    @Test
    void should_save_and_return_lecture() throws Exception {
        Lecture lecture = new Lecture(null,"30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png","abc.com","very good");
        when(lectureService.insertLecture(lecture)).thenReturn(lecture);
        String content = "{\"title\": \"30개 프로젝트로 배우는 프론트엔드 with React\", \"site\": \"fastcampus\", \"rate\": 2.9, \"image\": \"lecture4.png\", \"url\": \"abc.com\", \"description\": \"very good\"}";
        mockMvc.perform(post("/lecture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isCreated())
        .andDo(print());

        verify(lectureService).insertLecture(lecture);

    }

    @Test
    void should_return_lecture_with_id_1() throws Exception {
        Lecture lecture = new Lecture("1","30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png","abc.com","very good");
        String id = lecture.getId();
        when(lectureService.getLectureById(id)).thenReturn(Optional.of(lecture));
        mockMvc.perform(get("/lecture/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_return_404() throws Exception {
        String id ="2";
        when(lectureService.getLectureById(id)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/lecture/2"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}
