package com.wen.utils;


import com.wen.exception.SystemException;
import com.wen.pojo.entity.LoginUser;
import com.wen.pojo.enums.AppHttpCodeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 根据token获取用户ID
 */
public class SecurityUtils {
    /**
     * 获取Authentication对象
     */
    public static Authentication getAuthentication() {
        // 用户在完成登录后 Security 会将用户信息存储到SecurityContextHolder类中，所以可以从中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    /**
     * 从Authentication对象获取用户信息
     **/
    public static LoginUser getLoginUser() {
        Authentication authentication = getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser;
    }

    /**
     * 从用户信息获取用户id
     */
    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        Long userid = loginUser.getUser().getId();
        return userid;
    }

    /**
     * 判断是否为管理员，如果ID为1则说明为管理员
     */
    public static Boolean isAdmin() {
        Long id = getLoginUser().getUser().getId();
        return id != null && id.equals(1L);
    }
}