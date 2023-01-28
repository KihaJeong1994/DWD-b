package com.dwd.dwdb.model;

import com.dwd.dwdb.enums.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class Lecture {
    @Id
    private String id;
    private String title;
    private Site site;
    private Double rate;
    private String image;
}
