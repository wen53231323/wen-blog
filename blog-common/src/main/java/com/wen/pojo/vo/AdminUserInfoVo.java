package com.wen.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class AdminUserInfoVo {
    // 角色信息
    private List<String> role;

    // 权限信息
    private List<String> permissions;

    // 用户信息
    private UserInfoVo user;
}
