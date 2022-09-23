package com.wen.service;

import com.wen.pojo.dto.LoginUserDTO;
import com.wen.pojo.entity.User;
import com.wen.utils.ResponseResult;

public interface LoginService {

    // 登录
    ResponseResult login(LoginUserDTO loginUserDTO);

    // 退出登录
    ResponseResult logout();
}
