package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.mapper.UserMapper;
import com.wen.pojo.entity.LoginUser;
import com.wen.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    /**
     *  从数据库校验用户是否存在（验证身份就是UserDetails接口类，交给 认证管理器 进行匹配）
     *  （1）根据用户账号查询一条用户信息
     *  （2）校验用户是否存在，不存在则抛出异常
     *  （3）用户存在则返回 用户信息 和 权限信息列表
     *
     * */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getAccount,account);
        // 根据用户账号查询一条用户信息
        User user = userMapper.selectOne(lqw);
        // 校验用户是否存在，不存在则抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        // 用户存在则返回 用户信息 和 权限信息列表
        // 返回用户信息和权限信息列表（LoginUser实现了UserDetails接口）
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }
}
