package com.wen.service;

import com.wen.pojo.dto.LoginUserDTO;
import com.wen.utils.ResponseResult;

public interface AdminLoginService {
    // 登录
    ResponseResult login(LoginUserDTO loginUserDTO);

    // 退出登录
    ResponseResult logout();
}
