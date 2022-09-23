package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Navigation)表实体类
 *
 * @author makejava
 * @since 2022-08-30 12:06:30
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
@TableName("navigation")
public class Navigation {
    //网站id    
    private Long id;
    //网站图标    
    private String logo;
    //网站名称    
    private String name;
    //网站描述    
    private String description;
    //网站地址    
    private String address;
    //网站类别    
    private String category;
    //网站添加者    
    private Long createBy;
    //网站收录时间    
    private Date createTime;
    //网站修改者    
    private Long updateBy;
    //网站修改时间    
    private Date updateTime;
    //网站审核状态（0通过，1未通过，2未审核）    
    private String status;
    //网站删除标志（0未删除、1已删除）    
    private Integer delFlag;
}

