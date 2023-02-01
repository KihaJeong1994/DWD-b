package com.dwd.dwdb.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
public class LectureReview {
    @Id
    private String id;
    private String lectureId,review,createdBy;
    private double rate;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public LectureReview(String id, String lectureId, String review, String createdBy, double rate){
        this.id = id;
        this.lectureId = lectureId;
        this.review = review;
        this.createdBy = createdBy;
        this.rate = rate;
    }
}
