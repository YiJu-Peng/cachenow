package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.Resource;

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


    List<Resource> getResourcesByCategory(Integer categoryId);

    String getCategoryById(Integer categoryId);

    void addResource(Resource resource);

    List<Resource> getResourcesByTitle(String resourceTitle);
}
