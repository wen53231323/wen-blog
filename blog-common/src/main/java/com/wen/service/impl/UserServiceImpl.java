package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.exception.SystemException;
import com.wen.mapper.UserMapper;
import com.wen.pojo.dto.UserRegister;
import com.wen.pojo.entity.User;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.pojo.vo.UserInfoVo;
import com.wen.service.UserService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import com.wen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 获取用户信息
     * （1）根据token解析用户id
     * （2）根据用户id查询用户信息
     * （3）返回用户信息
     */
    @Override
    public ResponseResult getUserInfo() {
        // 根据token解析用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        User user = userMapper.selectById(userId);
        // 使用工具类进行bean拷贝封装为UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    /**
     * 更新用户信息
     */
    @Override
    public ResponseResult updateUserInfo(User user) {
        // 根据token解析id
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getId, userId);
        userMapper.update(user, lqw);
        return ResponseResult.okResult();
    }

    /**
     * 用户注册
     * （1）获取前台传递的数据进行非空判断
     * （2）判断数据库是否已经存在
     * （3）使用Spring Security提供的加密方式，对密码进行加密并存入数据库
     */
    @Override
    public ResponseResult register(UserRegister userRegister) {
        String account = userRegister.getAccount();
        String password = userRegister.getPassword();
        String email = userRegister.getEmail();
        String nickName = userRegister.getNickName();
        if (!StringUtils.hasText(account)) {
            throw new SystemException(AppHttpCodeEnum.USERACCOUNT_NOT_NULL);
        }
        if (!StringUtils.hasText(password)) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(email)) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(nickName)) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        // 判断数据库中账号是否已存在
        if (userAccountExist(account)) {
            throw new SystemException(AppHttpCodeEnum.ACCOUNT_EXIST);
        }
        // 判断数据库中邮箱是否已存在
        if (userEmailExist(email)) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        // 使用Spring Security提供的加密方式，对密码进行加密
        String encode = passwordEncoder.encode(password);

        User user = new User();
        user.setAccount(account);
        user.setPassword(encode);
        user.setEmail(email);
        user.setNickName(nickName);
        userMapper.insert(user);

        return ResponseResult.okResult();
    }

    /**
     * 根据前台传递来的用户账号，判断数据库中是否存在
     *
     * @param account 账号
     * @return 存在true，不存在false
     */
    public boolean userAccountExist(String account) {
        Integer i = userMapper.userAccountExist(account);
        if (i == null) {
            return false;
        }
        return true;
    }

    /**
     * 根据前台传递来的用户邮箱，判断数据库中是否存在
     *
     * @param email 邮箱
     * @return 存在true，不存在false
     */
    public boolean userEmailExist(String email) {
        Integer i = userMapper.userEmailExist(email);
        if (i == null) {
            return false;
        }
        return true;
    }

}
