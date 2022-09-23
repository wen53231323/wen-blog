package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.dto.CommentDTO;
import com.wen.pojo.entity.Comment;
import com.wen.utils.ResponseResult;

/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2022-09-06 16:02:58
 */
public interface CommentService extends IService<Comment> {

    // 查询评论列表
    ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize);

    // 发布评论
    ResponseResult addComment(CommentDTO commentDTO);
}

