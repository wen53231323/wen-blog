package com.wen.pojo.dto;

import lombok.Data;

@Data
public class UserListDTO {
    //用户账号
    private String account;
    //用户手机号
    private String phone;
    //用户账号状态（0正常、1禁用）
    private String status;
}
