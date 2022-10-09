package com.wen.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.mapper.UserRoleMapper;
import com.wen.pojo.entity.UserRole;
import com.wen.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 01:20:27
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

