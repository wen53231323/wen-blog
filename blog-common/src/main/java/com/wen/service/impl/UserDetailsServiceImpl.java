package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.mapper.MenuMapper;
import com.wen.mapper.UserMapper;
import com.wen.pojo.constants.SystemConstants;
import com.wen.pojo.entity.LoginUser;
import com.wen.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 从数据库校验用户是否存在（验证身份就是UserDetails接口类，交给 认证管理器 进行匹配）
     * （1）根据用户账号查询一条用户信息
     * （2）校验用户是否存在，不存在则抛出异常
     * （3）用户存在，判断是后台用户还是前台用户，
     * 如果是后台用户则返回 用户信息 和 权限信息
     * 如果是前台用户只返回 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getAccount, account);
        // 根据用户账号查询一条用户信息
        User user = userMapper.selectOne(lqw);
        // 校验用户是否存在，不存在则抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }
        // -------------------用户存在则返回 用户信息 和 权限信息列表，仅仅是后台用户才需要返回权限信息-------------------
        // 如果是后台用户则返回用户信息和权限信息
        if (user.getIsAdmin().equals(SystemConstants.IS_ADMIN)) {
            // 根据用户id获取对应权限信息（LoginUser实现了UserDetails接口）
            List<String> permissionsList = menuMapper.selectPermissionsByUserId(user.getId());
            LoginUser loginUser = new LoginUser(user, permissionsList);
            return loginUser;
        } else {
            // 如果是前台用户只返回用户信息（LoginUser实现了UserDetails接口）
            LoginUser loginUser = new LoginUser(user, null);
            return loginUser;
        }
    }
}
