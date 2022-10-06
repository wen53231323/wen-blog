package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.Menu;

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
}

