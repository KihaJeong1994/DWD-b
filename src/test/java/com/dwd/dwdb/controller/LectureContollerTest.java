package com.dwd.dwdb.controller;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.service.LectureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@WebMvcTest(LectureController.class)
public class LectureContollerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LectureService lectureService;

    @Test
    void should_return_lecture_list() throws Exception {
        when(lectureService.searchLecture(null, Site.inflearn,4.0)).thenReturn(Arrays.asList(
                new Lecture("1","스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술", Site.inflearn,4.3,"lecture1.png")
        ));
        mockMvc.perform(get("/lecture?site=inflearn&rate=4.0"))
                .andExpect(status().isOk());
//                .andExpect(content().string(containsString("스프링")));

    }

    @Test
    void should_save_and_return_lecture() throws Exception {
        Lecture lecture = new Lecture(null, "30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png");
        when(lectureService.insertLecture(lecture)).thenReturn(new Lecture("1", "30개 프로젝트로 배우는 프론트엔드 with React", Site.fastcampus, 2.9, "lecture4.png"));
        String content = "{\"title\": \"30개 프로젝트로 배우는 프론트엔드 with React\", \"site\": \"fastcampus\", \"rate\": 2.9, \"image\": \"lecture4.png\"}";
        mockMvc.perform(post("/lecture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isCreated());

        verify(lectureService).insertLecture(lecture);

    }

}