package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色表(Role)表实体类
 *
 * @author makejava
 * @since 2022-10-03 23:37:51
 */
// @SuppressWarnings注解是jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
@TableName("role")
public class Role{
    //角色ID    
    private Long id;
    //角色名称    
    private String roleName;
    //角色权限字符串    
    private String roleKey;
    //角色显示顺序    
    private Integer roleSort;
    //角色创建者ID    
    private Long createBy;
    //角色创建时间    
    private Date createTime;
    //角色更新者ID    
    private Long updateBy;
    //角色更新时间    
    private Date updateTime;
    //角色备注    
    private String remark;
    //角色状态（0正常、1禁用）    
    private String status;
    //角色删除标志（0未删除、1已删除）    
    private Integer delFlag;
}

