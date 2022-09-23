package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 分类表(Category)表实体类
 *
 */
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
//MyBatis-Puls提供的注解，设置实体类对应的表名
@TableName("category")
public class Category  {
    @TableId
    //分类id
    private Long id;
    //父分类id，没有父分类就为-1
    private Long pid;
    //分类名称
    private String name;
    //分类描述
    private String description;
    //分类创建者
    private Long createBy;
    //分类创建时间
    private Date createTime;
    //分类更新者
    private Long updateBy;
    //分类更新时间
    private Date updateTime;
    //分类状态（0正常、1禁用）
    private String status;
    //分类删除标志（0未删除、1已删除）
    private Integer delFlag;
}
