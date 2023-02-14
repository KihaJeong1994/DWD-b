package com.dwd.dwdb.controller;

import com.dwd.dwdb.model.LectureReview;
import com.dwd.dwdb.service.LectureReviewService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Disabled// TODO : test without spring security
public class LectureReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureReviewService lectureReviewService;

    @Test
    void should_save_and_return_review() throws Exception {
        LectureReview review = new LectureReview(null, "1", "너무너무 좋은 강의입니다!", "Flutter꿈나무", 5);
        when(lectureReviewService.insertLectureReview(review)).thenReturn(review);
        String content = "{\"lectureId\": \"1\", \"review\": \"너무너무 좋은 강의입니다!\", \"rate\": 5, \"createdBy\": \"Flutter꿈나무\"}";
        mockMvc.perform(post("/lecture/1/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void should_return_reviews() throws Exception {
        String lectureId = "1";
        Pageable pageable = PageRequest.of(0,20);
        Page<LectureReview> expectedLectures = new PageImpl<>(Arrays.asList(
                new LectureReview("gaec","1","너무너무 좋은 강의입니다!","Flutter꿈나무",5)
                ,new LectureReview("etecae","1","어서 다음 강의 출시 해주세요~","스프링master",4.5)
        ));
        when(lectureReviewService.getLectureReviewByLectureId(lectureId,pageable)).thenReturn(expectedLectures);
        mockMvc.perform(get("/lecture/1/review"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
