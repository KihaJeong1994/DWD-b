package com.dwd.dwdb.controller;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("")
    List<Lecture> searchLectures(@RequestParam(required = false) String title,@RequestParam(required = false) Site site,@RequestParam(required = false) Double rate){
        List<Lecture> lectures = lectureService.searchLecture(title, site, rate);
        return lectures;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Lecture insertLecture(@RequestBody Lecture lecture){
        Lecture lectureSaved = lectureService.insertLecture(lecture);
        return lectureSaved;
    }
}
