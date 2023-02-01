package com.dwd.dwdb.model;

import com.dwd.dwdb.enums.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    @Id
    private String id;
    private String title,image,url,description;
    private Site site;
    private Double rate;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public Lecture(String id,String title, Site site, Double rate, String image,String url, String description){
        this.id = id;
        this.title = title;
        this.site = site;
        this.rate = rate;
        this.image = image;
        this.url = url;
        this.description = description;
    }

}
