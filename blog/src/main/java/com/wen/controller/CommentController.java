package com.wen.controller;

import com.wen.pojo.dto.CommentDTO;
import com.wen.service.CommentService;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(tags = "评论", description = "评论相关接口")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "一级评论或二级回复展示", notes = "根据文章id查询文章评论列表")
    @GetMapping("/commentList/{articleId}/{pageNum}/{pageSize}")
    public ResponseResult commentList(@PathVariable Long articleId,@PathVariable Integer pageNum,@PathVariable Integer pageSize) {
        ResponseResult result = commentService.getCommentList(articleId, pageNum, pageSize);
        return result;
    }

    @ApiOperation(value = "发布一级评论或二级回复", notes = "一级评论或二级回复")
    @PostMapping
    public ResponseResult addComment(@RequestBody CommentDTO commentDTO) {
        ResponseResult result = commentService.addComment(commentDTO);
        return result;
    }

}
