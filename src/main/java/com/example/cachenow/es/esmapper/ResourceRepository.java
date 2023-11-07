package  com.example.cachenow.es.esmapper;
import com.example.cachenow.domain.Resource;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ResourceRepository extends ElasticsearchRepository<Resource, Integer> {

    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}")
    List<Resource> searchByTitleOrContent(String search);

}