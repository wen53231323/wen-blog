package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.exception.SystemException;
import com.wen.mapper.UserMapper;
import com.wen.pojo.dto.AddUserDTO;
import com.wen.pojo.dto.UserListDTO;
import com.wen.pojo.dto.UserRegister;
import com.wen.pojo.entity.Tag;
import com.wen.pojo.entity.User;
import com.wen.pojo.entity.UserRole;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.pojo.vo.PageVo;
import com.wen.pojo.vo.UserInfoVo;
import com.wen.service.UserRoleService;
import com.wen.service.UserService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import com.wen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

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
        if (checkAccountUnique(account)) {
            throw new SystemException(AppHttpCodeEnum.ACCOUNT_EXIST);
        }
        // 判断数据库中邮箱是否已存在
        if (checkEmailUnique(email)) {
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

    @Override
    public ResponseResult pageUserList(Integer pageNum, Integer pageSize, UserListDTO userListDTO) {
        //分页查询
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(userListDTO.getAccount()), User::getAccount, userListDTO.getAccount());
        lqw.like(StringUtils.hasText(userListDTO.getPhone()), User::getPhone, userListDTO.getPhone());
        lqw.like(StringUtils.hasText(userListDTO.getStatus()), User::getStatus, userListDTO.getStatus());
        Page<User> page = new Page<>(pageNum, pageSize); // 分页查询指定页面
        // userMapper.selectPage(page, lqw); // 添加查询条件方式一
        page(page, lqw);// 添加查询条件方式二
        List<User> records = page.getRecords();// 获取全部数据列表
        long total = page.getTotal();// 获取记录的总数
        PageVo pageVo = new PageVo(records, total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addUser(AddUserDTO addUserDTO) {
        // 检查账号是否为空
        if (!StringUtils.hasText(addUserDTO.getAccount())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        // 检查账号是否唯一
        if (checkAccountUnique(addUserDTO.getAccount())) {
            throw new SystemException(AppHttpCodeEnum.ACCOUNT_EXIST);
        }
        // 检查手机号是否唯一
        if (checkPhoneUnique(addUserDTO.getPhone())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        // 检查邮箱是否唯一
        if (checkEmailUnique(addUserDTO.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        User user = BeanCopyUtils.copyBean(addUserDTO, User.class);

        // 使用Spring Security提供的加密方式，对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        // 如果用户存在角色，则添加角色到 用户角色关联表
        if (addUserDTO.getRoleIds() != null && addUserDTO.getRoleIds().length > 0) {
            List<UserRole> sysUserRoles = Arrays.stream(addUserDTO.getRoleIds())
                    .map(roleId -> new UserRole(user.getId(), roleId))
                    .collect(Collectors.toList());
            userRoleService.saveBatch(sysUserRoles);
        }

        return ResponseResult.okResult();
    }

    /**
     * 根据前台传递来的用户账号，判断数据库中是否存在
     *
     * @param account 账号
     * @return 存在true，不存在false
     */
    @Override
    public boolean checkAccountUnique(String account) {
        Object o = userMapper.userAccountExist(account);
        if (o == null) {
            return false;
        }
        return true;
    }

    /**
     * 根据前台传递来的手机号，判断数据库中是否存在
     *
     * @param phone 账号
     * @return 存在true，不存在false
     */
    @Override
    public boolean checkPhoneUnique(String phone) {
        Object o = userMapper.userPhoneExist(phone);
        if (o == null) {
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
    @Override
    public boolean checkEmailUnique(String email) {
        Object o = userMapper.userEmailExist(email);
        if (o == null) {
            return false;
        }
        return true;
    }
}
