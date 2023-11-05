package com.example.cachenow.es.esservice;

import com.example.cachenow.domain.Resource;
import com.example.cachenow.es.esmapper.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void transferDataFromMySQLToES() {
        // 从MySQL中检索数据
        Iterable<Resource> resourceIterable = resourceRepository.findAll();
        List<Resource> resourceList = new ArrayList<>();
        resourceIterable.forEach(resourceList::add);

        // 将数据保存到Elasticsearch中
        resourceRepository.saveAll(resourceList);
    }

    public List<Resource> searchByTitleAndContent(String title) {
        return resourceRepository.searchByTitleAndContent(title);
    }
}