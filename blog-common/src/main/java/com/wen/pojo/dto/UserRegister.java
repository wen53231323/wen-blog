package com.wen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @SuppressWarnings注解是jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class UserRegister {
    //用户账号
    private String account;
    //用户昵称
    private String nickName;
    //用户邮箱
    private String email;
    //用户密码
    private String password;
}
