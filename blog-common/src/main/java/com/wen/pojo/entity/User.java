package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-08-30 16:49:32
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
@TableName("user")
public class User {
    //用户id    
    private Long id;
    //用户账号
    private String account;
    //用户密码    
    private String password;
    //用户头像    
    private String avatar;
    //用户昵称    
    private String nickName;
    //用户性别（0男、1女、2未设置）    
    private String sex;
    //用户手机号    
    private String phone;
    //用户邮箱    
    private String email;

    //用户创建者
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    //用户创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //用户更新者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //用户更新时间    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //用户账号状态（0正常、1禁用）    
    private String status;
    //是否是管理员（0是，1不是）    
    private String isAdmin;
    //用户删除标志（0未删除、1已删除）    
    private Integer delFlag;
}

