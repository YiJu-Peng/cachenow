package com.example.cachenow.es.esdomain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "resource_index")
public class Resource {
    @Id
    private Integer resourceId;
    
    private String title;
    
    private String content;
    
    private Integer categoryId;
    
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date createdAt;
    
    private Integer uploaderId;
    
    private Float rating;
    
    private Integer totalRatings;

    // Getter and setter methods
}