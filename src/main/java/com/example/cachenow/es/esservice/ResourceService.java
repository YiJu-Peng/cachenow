package com.example.cachenow.es.esservice;

import com.example.cachenow.domain.Resource;
import com.example.cachenow.dto.ResourceDTO;
import com.example.cachenow.es.esmapper.ResourceRepository;
import com.example.cachenow.mapper.ResourceDao;
import com.example.cachenow.service.impl.ResourceServiceImpl;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.cachenow.utils.Constants.UserConstants.PAGE_SIZE;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceServiceImpl resourceService;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;


    /**
     * 将所有的数据都放入es
     * 这个操作会扫描整个resource表
     * 慎用
     */
    public void transferDataFromMySQLToESAll() {
        // 从MySQL中检索数据
        Iterable<Resource> resourceIterable = resourceDao.findAll();
        List<Resource> resourceList = new ArrayList<>();
        resourceIterable.forEach(resourceList::add);

        // 将数据保存到Elasticsearch中
        resourceRepository.saveAll(resourceList);
    }

    public List<ResourceDTO> searchByTitleAndContent(String search, int pageNum) {
        // 创建分页请求对象
        Pageable pageable = PageRequest.of(pageNum, Math.toIntExact(PAGE_SIZE));
        // 构建布尔查询器
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title", search))
                .should(QueryBuilders.matchQuery("content", search));


        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .build();

        // 执行分页搜索操作
        SearchHits<Resource> searchResults = elasticsearchTemplate.search(searchQuery, Resource.class);

        // 处理分页搜索结果
        if (searchResults.hasSearchHits()) {
            //先在es中进行查询
            List<ResourceDTO> resources = new ArrayList<>();
            for (SearchHit<Resource> searchHit : searchResults.getSearchHits()) {
                final Resource content = searchHit.getContent();
                resources.add(new ResourceDTO(content));
            }
            return resources;
        } else {
            return resourceService.ResourcesSearch(search,pageNum);
            // 没有找到匹配的结果,进入mysql数据库进行匹配(匹配只是很简单地匹配,就是将search前后加%)
        }
    }

}