package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.Resource;
import com.example.cachenow.dto.ResourceDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
public interface IResourceService extends IService<Resource> {


    List<ResourceDTO> ResourcesSearch(String search, int pageNum);

    List<Resource> getResourcesByCategory(Integer categoryId, int pageNumber);

    void addResource(Resource resource);

    List<Resource> getResourcesByUserId(Integer userId);

    void editResource(Long resourceId, ResourceDTO request);

    void rateResource(long resourceId, int rate);

    void addComment(Long resourceId, String content, Long superId);

    void deleteResource(Integer resourceId);
}
