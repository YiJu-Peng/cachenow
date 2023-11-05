package com.example.cachenow.es.esmapper;

import com.example.cachenow.domain.Resource;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ResourceRepository extends ElasticsearchRepository<Resource, String> {

    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}")
    List<Resource> searchByTitleAndContent(String search);
}