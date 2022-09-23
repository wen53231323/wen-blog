package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2022-09-07 12:21:48
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
@TableName("comment")
public class Comment {
    //评论id    
    private Long id;
    //评论内容    
    private String content;
    //评论所属id（给谁评论的）    
    private Long belongId;
    //评论点赞数    
    private Integer likeNum;

    //评论发表者id（谁评论的）
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    //评论发表时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //评论更新者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    //评论更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //评论删除标志（0未删除、1已删除）
    private Integer delFlag;
}

