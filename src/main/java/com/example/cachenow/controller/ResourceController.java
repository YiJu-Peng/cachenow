package com.example.cachenow.controller;


import com.example.cachenow.domain.Resource;
import com.example.cachenow.dto.RatingDTO;
import com.example.cachenow.dto.ResourceDTO;
import com.example.cachenow.dto.Result;
import com.example.cachenow.dto.Search;
import com.example.cachenow.es.esservice.ResourceService;
import com.example.cachenow.service.impl.ResourceServiceImpl;
import com.example.cachenow.utils.other.UserHolder;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@ResponseBody
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    ResourceServiceImpl resourceService;
    @Autowired
    ResourceService resourceESService;
    @Autowired
    RabbitTemplate  rabbitTemplate;

    /**
     * 添加资源,并同步到es中
     * @param resourceDTO 请求上传的资源
     * @return 无
     */
    // 添加资源
    @PostMapping("/create")
    public Result addResource(@RequestBody ResourceDTO resourceDTO) {
        resourceDTO.setUserId(UserHolder.getUser().getId());
        Resource resource = new Resource(resourceDTO);
        resourceService.addResource(resource);
        return Result.ok("成功上传");
    }

    /**
     * 查询框使用的模糊查询,可以查询所有的有关的资料,es中没有地话就会进入mysql查(mysql查地很简单就是前后加%)
     * @param search 要查的关键字,可以是标题和内容
     * @return 返回匹配资源列表
     */
    @PostMapping("/search")
    public Result getResource(@RequestBody Search search) {
        final List<ResourceDTO> resourcesByTitle =
                resourceESService.searchByTitleAndContent(search.getSearch().toString(), search.getPageNumber());
        final int size = resourcesByTitle.size();
        return Result.ok(resourcesByTitle, (long) size);
    }


    /**
     * 按分类检索资源
     * @param search 分类进行检索的分类id
     * @return 返回符合分类的资源
     */
    @PostMapping("/category/")
    public Result getResourcesByCategory(@RequestBody Search search) {
        final List<Resource> resourcesByCategory = resourceService.
                getResourcesByCategory((Integer) search.getSearch(),search.getPageNumber());
        final Long size = (long) resourcesByCategory.size();
        return Result.ok(resourcesByCategory, size);
    }

    /**
     * 通过userid获取个人资源
     * @param userId 个人id
     * @return 返回个人发布的资源
     */
    @GetMapping("/{userId}")
    public Result getCategoryById(@PathVariable Integer userId) {
        final List<Resource> resourcesByUserId = resourceService.getResourcesByUserId(userId);
        return Result.ok(resourcesByUserId, (long) resourcesByUserId.size());
    }

    /**
     * 编辑个人资源
     * @param resourceId 资源的id
     * @param request 请求进行更改的资源
     * @return 无
     */
    @PostMapping("/edit/{resourceId}")
    public Result editResource(
            @PathVariable("resourceId") Long resourceId,
            @RequestBody ResourceDTO request) {
        resourceService.editResource(resourceId, request);
        return Result.ok("资源编辑成功");
    }

    /**
     * 用户进行评分的接口
     * @param rating 打的分数和id
     * @return 无
     */
    @PostMapping("/rating")
    public Result rateResource(
            @RequestBody RatingDTO rating) {
        resourceService.rateResource(rating.getResourceId(), rating.getRate());
        return Result.ok("资源评分成功");
    }

    @PostMapping("/comment/{resourceId}")
    public Result commentResource(
            @PathVariable("resourceId") Long resourceId,
            @RequestBody String content) {
        if (resourceId==null){
            return Result.fail("resourceId cannot be null");
        }
        if (content==null){
            return Result.fail("content cannot be null");
        }
        resourceService.addComment(resourceId, content);
        return Result.ok("评论成功");
    }

    @DeleteMapping("/delete/{resourceId}")
    public Result commentResource(@PathVariable Integer resourceId) {
        if (resourceId==null){
            return Result.fail("resourceId cannot be null");
        }
        resourceService.deleteResource(resourceId);
        return Result.ok("资源删除成功");
    }
}

