package com.example.cachenow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.Comment;
import com.example.cachenow.mapper.CommentDao;
import com.example.cachenow.service.ICommentService;
import com.example.cachenow.utils.annotation.BatchQuery;
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
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements ICommentService {
    @Override
    @BatchQuery(primaryKey = "comment_id")
    public List<Comment> getCommentsByResourceId(Integer resourceId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_id", resourceId);
        return list(queryWrapper);
    }
}
