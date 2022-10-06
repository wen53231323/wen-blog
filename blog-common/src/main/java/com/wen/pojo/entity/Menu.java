package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 权限表(Menu)表实体类
 *
 * @author makejava
 * @since 2022-10-03 23:37:30
 */
// @SuppressWarnings注解是jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
@TableName("menu")
@Accessors(chain = true) // 将Menu变成链式编程，set方法的返回值类型可以是当前类
public class Menu {
    //菜单ID    
    private Long id;
    // 链式编程下的set
    public Menu setId(Long id) {
        this.id = id;
        return this;
    }

    //菜单名称
    private String menuName;
    //父菜单ID    
    private Long parentId;
    //菜单显示顺序    
    private Integer orderNum;
    //权限标识    
    private String permissions;
    //菜单图标    
    private String icon;
    //菜单路由地址    
    private String path;
    //菜单路由路径    
    private String component;
    //菜单类型（M目录、C菜单、F按钮）    
    private String menuType;
    //菜单状态（0显示、1隐藏）    
    private String visible;
    //菜单是否为外链（0是、1否）    
    private Integer isFrame;
    //菜单创建者ID    
    private Long createBy;
    //菜单创建时间    
    private Date createTime;
    //菜单更新者ID    
    private Long updateBy;
    //菜单更新时间    
    private Date updateTime;
    //菜单备注    
    private String remark;
    //菜单状态（0正常、停用）    
    private String status;
    //菜单删除标志（0未删除、1已删除）    
    private Integer delFlag;

    //子菜单
    // 文章所属分类名（@TableField(exist = false)表示表中没有这个字段，防止报错）
    @TableField(exist = false)
    private List<Menu> children;
}

