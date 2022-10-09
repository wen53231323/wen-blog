package com.wen.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.mapper.RoleMenuMapper;
import com.wen.pojo.entity.RoleMenu;
import com.wen.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色权限关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 15:56:17
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

