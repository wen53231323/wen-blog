package com.wen.controller;
import com.wen.exception.SystemException;
import com.wen.pojo.dto.LoginUserDTO;
import com.wen.pojo.entity.User;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.service.LoginService;
import com.wen.utils.ResponseResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录与退出", description = "登录和退出登录相关接口")
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录", notes = "登录接口")
    @PostMapping("/login")
    // @RequestBody注解：将请求体信息与控制器方法的形参进行绑定
    public ResponseResult login(@RequestBody LoginUserDTO loginUserDTO) {
        // 统一异常处理
        if(!StringUtils.hasText(loginUserDTO.getAccount())){
            // 提示必须要传账号
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        ResponseResult login = loginService.login(loginUserDTO);
        return login;
    }

    @ApiOperation(value = "登出", notes = "退出登录接口")
    @PostMapping("/logout")
    public ResponseResult logout() {
        ResponseResult logout = loginService.logout();
        return logout;
    }
}
