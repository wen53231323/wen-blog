package com.wen.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.mapper.RoleMapper;
import com.wen.pojo.entity.Role;
import com.wen.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-10-03 23:37:51
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleByUserId(Long userId) {
        // （1）如果是管理员,返回集合只需要admin
        if (userId == 1L) {
            ArrayList<String> list = new ArrayList<>();
            list.add("admin");
            return list;
        }

        // （2）否则查询用户所具有的角色权限信息
        List<String> roleKeyList = roleMapper.selectRoleKeyByUserId(userId);


        return roleKeyList;
    }
}

