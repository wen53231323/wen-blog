package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Article)表实体类
 *
 * @author makejava
 * @since 2022-08-27 10:56:21
 */

// @SuppressWarnings注解是jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
//MyBatis-Puls提供的注解，设置实体类对应的表名
@TableName("article")
public class Article {
    //文章id
    @TableId("id")
    private Long id;
    //文章分类id
    private Long categoryId;
    // 文章所属分类名（@TableField(exist = false)表示表中没有这个字段，防止报错）
    @TableField(exist = false)
    private String categoryName;
    //文章标题
    private String title;
    //文章摘要
    private String summary;
    //文章内容
    private String content;
    //文章略缩图
    private String thumbnail;
    //文章访问量
    private Integer viewCount;
    //文章评论数
    private String commentCount;
    //文章创建者
    private Long createBy;
    //文章创建时间
    private Date createTime;
    //文章更新者
    private Long updateBy;
    //文章更新时间
    private Date updateTime;
    //文章是否允许评论（0不允许评论，1允许评论）
    private String isComment;
    //文章状态（0已发布，1草稿）
    private String status;
    //文章删除标志（0未删除，1已删除）
    private Integer delFlag;
    //文章是否置顶（0不置顶，1置顶）
    private String isTop;
}

