package com.example.cachenow.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.Comment;
import com.example.cachenow.domain.Resource;
import com.example.cachenow.dto.ResourceDTO;
import com.example.cachenow.mapper.ResourceDao;
import com.example.cachenow.service.IResourceService;
import com.example.cachenow.utils.other.UserHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cachenow.utils.Constants.UserConstants.PAGE_SIZE;

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
    private RabbitTemplate  rabbitTemplate;

    @Autowired
    private CommentServiceImpl commentService;

    // 添加资源,异步双写
    @Transactional
    public void addResource(Resource resource) {
        resourceMapper.insert(resource);
        rabbitTemplate.convertAndSend("ResourceQueue",resource);
    }

    @Override
    public List<Resource> getResourcesByUserId(Integer userId) {
        return resourceMapper.
                selectList(new QueryWrapper<Resource>().
                        eq("uploader_id", userId));
    }

    @Override
    public void editResource(Long resourceId, ResourceDTO request) {
        final Resource resource = new Resource(request);
        resource.setResource_id(resourceId);
        resourceMapper.updateById(resource);
    }

    @Override
    public void rateResource(long resourceId, int rate) {
        final Resource resource = resourceMapper.selectOne(new QueryWrapper<Resource>().
                eq("resource_id", resourceId));
        Integer totalRatings = resource.getTotal_ratings();
        if (totalRatings==null) {
            totalRatings=0;
        }
        final float v = totalRatings * resource.getRating();
        final float newRating = (v + rate) / (totalRatings + 1);
        resource.setRating(newRating);
        resource.setTotal_ratings(totalRatings + 1);
        resource.setResource_id(resourceId);
        resourceMapper.updateById(resource);
    }

    @Override
    public void addComment(Long resourceId, String content, Long superId) {
        final Comment comment = new Comment();
        comment.setComment(content);
        comment.setResource_id(Math.toIntExact(resourceId));
        comment.setUser_id(Math.toIntExact(UserHolder.getUser().getId()));
        comment.setCreated_at(java.time.LocalDateTime.now());
        if (superId != null) {
            comment.setSuper_id(Math.toIntExact(superId));
        }
        commentService.save(comment);
    }

    @Override
    public void deleteResource(Integer resourceId) {
        rabbitTemplate.convertAndSend("ResourceQueueDelete",resourceId);
        final int i = resourceMapper.deleteById(resourceId);
        if (i == 0) {
            throw new RuntimeException("删除失败,未找到相关资源");
        }
    }

    /**
     * 进行模糊查询,最好使用es中的,这个是es启动失败备用的
     *
     * @param search
     * @return
     */
    public List<ResourceDTO> ResourcesSearch(String search, int pageNum) {
        Page<Resource> page = new Page<>(pageNum, PAGE_SIZE);
        QueryWrapper<Resource> like = new QueryWrapper<Resource>()
                .like("content", "%" + search + "%")
                .or().like("title", "%" + search + "%");
        Page<Resource> resourcePage = resourceMapper.selectPage(page, like);
        List<Resource> records = resourcePage.getRecords();

        return records.stream()
                .map(resource ->
                        new ResourceDTO(resource.getTitle(),
                                resource.getContent(),
                                resource.getUploader_id()))
                .collect(Collectors.toList());
    }

    // 按分类检索资源
    public List<Resource> getResourcesByCategory(Integer categoryId, int pageNumber) {
        final Page<Resource> Page = new Page<>(pageNumber, PAGE_SIZE);
        return resourceMapper
                .selectPage(Page, new QueryWrapper<Resource>()
                .eq("category_id", categoryId)).getRecords();
    }


}
