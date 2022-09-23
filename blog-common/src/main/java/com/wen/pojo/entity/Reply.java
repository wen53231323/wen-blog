package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论回复表(Reply)表实体类
 *
 * @author makejava
 * @since 2022-09-07 12:21:59
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
@TableName("reply")
public class Reply {
    //回复id    
    private Long id;
    //回复的目标id（给哪个用户回复）    
    private Long toUserId;
    //回复内容    
    private String content;
    //回复时间    
    private Date createTime;
    //回复删除标志（0未删除、1已删除）    
    private Integer delFlag;
    //回复的父评论id（针对哪条评论的回复）    
    private Long commentId;
    //回复者id（谁回复的）    
    private Long createBy;
    //回复更新者    
    private Long updateBy;
    //回复更新时间    
    private Date updateTime;
}

