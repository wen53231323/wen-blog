package com.wen.controller;

import com.wen.annotations.SystemLog;
import com.wen.pojo.dto.UserRegister;
import com.wen.pojo.entity.User;
import com.wen.service.UserService;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户", description = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户信息展示", notes = "用户信息展示")
    @GetMapping("/userInfo")
    @SystemLog(businessName = "访问用户中心")
    public ResponseResult getUserInfo() {
        ResponseResult result = userService.getUserInfo();
        return result;
    }

    @ApiOperation(value = "用户信息更新", notes = "用户信息更新接口")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        ResponseResult result = userService.updateUserInfo(user);
        return result;
    }

    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    @PostMapping("/register")
    public ResponseResult UserRegister(@RequestBody UserRegister userRegister) {
        ResponseResult result = userService.register(userRegister);
        return result;
    }

}
