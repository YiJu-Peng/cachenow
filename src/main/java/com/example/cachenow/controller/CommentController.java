package com.example.cachenow.controller;


import com.example.cachenow.domain.Comment;
import com.example.cachenow.dto.Result;
import com.example.cachenow.service.impl.CommentServiceImpl;
import com.example.cachenow.service.impl.ResourceServiceImpl;
import com.example.cachenow.utils.annotation.SensitiveWordFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private ResourceServiceImpl resourceService;
    /**
     * 返回某个资源的所有的评论
     * @param resourceId 资源的id
     * @return 返回所有的评论
     */
    @GetMapping("/{resourceId}")
    public List<Comment> getCommentsByResourceId(@PathVariable Integer resourceId) {
        return commentService.getCommentsByResourceId(resourceId);
    }
    /**
     * 对资源进行评论
     * @param resourceId 资源的id
     * @param content 评论的内容
     * @return 是否评论成功
     */
    @SensitiveWordFilter
    @PostMapping("/{resourceId}/{superId}")
    public Result commentResource(
            @PathVariable("resourceId") Long resourceId,
            @PathVariable(required = false) Long superId,
            @RequestBody String content) {
        if (resourceId == null){
            return Result.fail("resourceId cannot be null");
        }
        if (content == null){
            return Result.fail("content cannot be null");
        }
        resourceService.addComment(resourceId, content, superId);
        return Result.ok("评论成功");
    }
}

