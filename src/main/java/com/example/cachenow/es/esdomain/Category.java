package com.example.cachenow.es.esdomain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "category_index")
public class Category {
    @Id
    private Integer categoryId;
    
    private String name;

    // Getter and setter methods
}