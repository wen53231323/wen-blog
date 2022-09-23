package com.wen.service;


import com.wen.pojo.dto.UserRegister;
import com.wen.pojo.entity.User;
import com.wen.utils.ResponseResult;

public interface UserService {
    // 获取用户信息
    ResponseResult getUserInfo();

    // 更新用户信息
    ResponseResult updateUserInfo(User user);

    // 用户注册接口
    ResponseResult register(UserRegister userRegister);
}
