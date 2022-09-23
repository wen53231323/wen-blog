package com.wen.pojo.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装前端传递的json，用于存储评论相关字段
 */

@ApiModel(description = "评论入参")
// @SuppressWarnings注解是jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class CommentDTO {
    // 文章id
    private Long articleId;

    // 一级评论id
    private Long commentId;

    // 回复的目标id（给哪个用户回复）
    private Long toUserId;

    // 评论内容或回复内容
    private String content;

}
