package com.wen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo {
    // token
    private String token;
    // 用户信息
    private UserInfoVo userInfo;
}