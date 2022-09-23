package com.wen.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserInfoVo {
    // 用户id
    private Long id;
    // 用户头像
    private String avatar;
    // 用户昵称
    private String nickName;
    // 用户性别（0男、1女、2未设置）
    private String sex;
    // 用户邮箱
    private String email;
}