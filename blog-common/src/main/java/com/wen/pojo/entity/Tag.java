package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Tag)表实体类
 *
 * @author makejava
 * @since 2022-10-06 15:49:23
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
@TableName("tag")
public class Tag {
    //标签ID    
    private Long id;
    //标签名称    
    private String name;
    //标签创建者
    private Long createBy;
    //标签创建时间
    private Date createTime;
    //标签更新者
    private Long updateBy;
    //标签更新时间
    private Date updateTime;
    //标签状态（0正常、1禁用）
    private String status;
    //标签删除标志（0未删除，1已删除）
    private Integer delFlag;
}

