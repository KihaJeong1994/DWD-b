package com.dwd.dwdb.controller;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.exception.ResourceNotFoundException;
import com.dwd.dwdb.model.Lecture;
import com.dwd.dwdb.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
@CrossOrigin// TODO : find global option to allow cross origin
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("")
    ResponseEntity<List<Lecture>> searchLectures(@RequestParam(required = false) String title, @RequestParam(required = false) Site site, @RequestParam(required = false) Double rate){
        List<Lecture> lectures = lectureService.searchLecture(title, site, rate);
        return new ResponseEntity<>(lectures,HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<Lecture> insertLecture(@RequestBody Lecture lecture){
        Lecture lectureSaved = lectureService.insertLecture(lecture);
        return new ResponseEntity<>(lectureSaved,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Lecture> getLectureById(@PathVariable("id") String id){
        try {
            Optional<Lecture> lecture = lectureService.getLectureById(id);
            return new ResponseEntity<>(lecture.get(),HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
