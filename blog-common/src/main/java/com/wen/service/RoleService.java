package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.dto.EditRoleDTO;
import com.wen.pojo.dto.RoleListDTO;
import com.wen.pojo.entity.Role;
import com.wen.utils.ResponseResult;

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

    // 查询角色列表
    ResponseResult pageRoleList(Integer pageNum, Integer pageSize, RoleListDTO roleListDTO);

    // 修改角色
    ResponseResult edit(EditRoleDTO editRoleDTO);
}

