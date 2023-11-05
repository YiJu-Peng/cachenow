package com.example.cachenow.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.Resource;
import com.example.cachenow.mapper.CategoryDao;
import com.example.cachenow.mapper.ResourceDao;
import com.example.cachenow.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements IResourceService {
    @Autowired
    private ResourceDao resourceMapper;

    @Autowired
    private CategoryDao categoryMapper;

    // 添加资源
    public void addResource(Resource resource) {
        resourceMapper.insert(resource);
    }

    @Override
    public List<Resource> getResourcesByTitle(String resourceTitle) {
        return resourceMapper.selectList(new QueryWrapper<Resource>()
                .like("title", "%" + resourceTitle + "%"));
    }

    // 按分类检索资源
    public List<Resource> getResourcesByCategory(Integer categoryId) {
        return resourceMapper.selectList(new QueryWrapper<Resource>()
                .eq("category_id", categoryId));
    }

    // 获取资源的分类信息
    public String getCategoryById(Integer categoryId) {
        return categoryMapper.selectById(categoryId).getName();
    }
}
