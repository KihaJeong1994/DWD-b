package com.dwd.dwdb.model;

import com.dwd.dwdb.enums.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
public class Lecture {
    @Id
    private String id;
    private String title;
    private Site site;
    private Double rate;
    private String image;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public Lecture(String title, Site site, Double rate, String image){
        this.title = title;
        this.site = site;
        this.rate = rate;
        this.image = image;
    }
}
