package com.example.cachenow.es.esdomain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "user_index")
public class User {
    @Id
    private Integer userId;
    
    private String username;
    
    private String contact;
    
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date createdAt;
    
    private String icon;
    
    private String email;

    // Getter and setter methods
}