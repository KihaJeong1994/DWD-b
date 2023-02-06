package com.dwd.dwdb.controller;

import com.dwd.dwdb.model.LectureReview;
import com.dwd.dwdb.service.LectureReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
@CrossOrigin
public class LectureReviewController {

    private final LectureReviewService lectureReviewService;

    @GetMapping("/{lectureId}/review")
    ResponseEntity<Page<LectureReview>> getLectureReviewById(@PathVariable("lectureId") String lectureId, Pageable pageable){
        return new ResponseEntity<>(lectureReviewService.getLectureReviewByLectureId(lectureId,pageable), HttpStatus.OK);
    }

    @PostMapping("/{lectureId}/review")
    ResponseEntity<LectureReview> insertLectureReview(@PathVariable("lectureId") String lectureId,@RequestBody LectureReview lectureReview){
        return new ResponseEntity<>(lectureReviewService.insertLectureReview(lectureReview), HttpStatus.CREATED);
    }
}
