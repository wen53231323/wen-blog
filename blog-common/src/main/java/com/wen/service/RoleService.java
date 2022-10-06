package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.Role;

import java.util.List;

/**
 * 角色表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-10-03 23:37:51
 */
public interface RoleService extends IService<Role> {

    // 根据用户id查询角色信息
    List<String> selectRoleByUserId(Long id);
}

