package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.Menu;
import com.wen.utils.ResponseResult;

import java.util.List;

/**
 * 权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-10-03 23:37:31
 */
public interface MenuService extends IService<Menu> {

    // 根据用户id查询权限信息
    List<String> selectPermissionsByUserId(Long userId);

    // 根据用户id查询用户权限列表
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    // 查询菜单列表，根据菜单名称和菜单名称模糊查询
    List<Menu> selectMenuList(String menuName, String status);

    // 根据id获取菜单详情
    ResponseResult getInfo(Long id);

    // 修改菜单
    ResponseResult updateMenu(Menu menu);

    // 根据角色id查询权限列表
    List<Long> selectMenuListByRoleId(Long roleId);
}

