package com.wen.config.security;

import com.alibaba.fastjson.JSON;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.utils.ResponseResult;
import com.wen.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义SpringSecurity授权失败处理器
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        // 打印异常信息
        authException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH );
        // 对象 —> 字符串
        String json = JSON.toJSONString(result);
        // 使用工具类将字符串渲染到客户端
        WebUtils.renderString(response, json);
    }
}
