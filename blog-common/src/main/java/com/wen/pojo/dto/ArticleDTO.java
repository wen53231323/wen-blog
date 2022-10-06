package com.wen.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wen.pojo.entity.ArticleTag;
import com.wen.pojo.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

// @SuppressWarnings注解是jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class ArticleDTO {
    //文章分类id
    private Long categoryId;
    // 文章所属分类名
    private String categoryName;
    // 文章标签
    private List<Long> tags;

    //文章标题
    private String title;
    //文章摘要
    private String summary;
    //文章内容
    private String content;
    //文章略缩图
    private String thumbnail;

    //文章是否允许评论（0不允许评论，1允许评论）
    private String isComment;
    //文章状态（0已发布，1草稿）
    private String status;
    //文章是否置顶（0不置顶，1置顶）
    private String isTop;

}
