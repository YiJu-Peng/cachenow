package com.example.cachenow.es.esdomain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "tag_index")
public class Tag {
    @Id
    private Integer tagId;
    
    private String name;
    
    private Integer resourceId;

    // Getter and setter methods
}