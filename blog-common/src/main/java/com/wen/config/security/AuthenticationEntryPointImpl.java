package com.wen.config.security;

import com.alibaba.fastjson.JSON;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.utils.ResponseResult;
import com.wen.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义SpringSecurity认证失败处理器
 */

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 打印异常信息
        authException.printStackTrace();
        // BadCredentialsException：当用户名或者密码错误抛出此异常
        // InsufficientAuthenticationException：权限不足或需要登录时抛出此异常
        ResponseResult result=null;
        if(authException instanceof BadCredentialsException){
            // 这里返回的消息是异常类型的消息
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或者授权失败");
        }

        // 对象 —> 字符串
        String json = JSON.toJSONString(result);
        // 使用工具类将字符串渲染到客户端
        WebUtils.renderString(response, json);
    }
}
