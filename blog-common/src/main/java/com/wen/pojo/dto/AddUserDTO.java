package com.wen.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddUserDTO {
    //用户账号
    private String account;
    //用户密码
    private String password;
    //用户昵称
    private String nickName;
    //用户手机号
    private String phone;
    //用户账号状态（0正常、1禁用）
    private String status;
    //用户性别（0男、1女、2未设置）
    private String sex;
    //用户邮箱
    private String email;
    //用户角色
    private Long[] roleIds;
}
