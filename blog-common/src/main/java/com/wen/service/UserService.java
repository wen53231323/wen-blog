package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.dto.AddUserDTO;
import com.wen.pojo.dto.UserListDTO;
import com.wen.pojo.dto.UserRegister;
import com.wen.pojo.entity.User;
import com.wen.pojo.entity.UserFollow;
import com.wen.utils.ResponseResult;

public interface UserService extends IService<User> {
    // 获取用户信息
    ResponseResult getUserInfo();

    // 更新用户信息
    ResponseResult updateUserInfo(User user);

    // 用户注册接口
    ResponseResult register(UserRegister userRegister);

    // 用户列表
    ResponseResult pageUserList(Integer pageNum, Integer pageSize, UserListDTO userListDTO);

    // 检查账号是否唯一
    boolean checkAccountUnique(String userName);

    // 检查手机号是否唯一
    boolean checkPhoneUnique(String phone);

    // 检查邮箱是否唯一
    boolean checkEmailUnique(String email);

    // 后台-添加用户
    ResponseResult addUser(AddUserDTO addUserDTO);
}
