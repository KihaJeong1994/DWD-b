package com.dwd.dwdb.controller;

import com.dwd.dwdb.model.LectureReview;
import com.dwd.dwdb.service.LectureReviewService;
import lombok.RequiredArgsConstructor;
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
    ResponseEntity<List<LectureReview>> getLectureReviewById(@PathVariable("lectureId") String lectureId){
        return new ResponseEntity<>(lectureReviewService.getLectureReviewByLectureId(lectureId), HttpStatus.OK);
    }

    @PostMapping("/{lectureId}/review")
    ResponseEntity<LectureReview> insertLectureReview(@PathVariable("lectureId") String lectureId,@RequestBody LectureReview lectureReview){
        return new ResponseEntity<>(lectureReviewService.insertLectureReview(lectureReview), HttpStatus.CREATED);
    }
}
