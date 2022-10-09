package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-03 23:37:30
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    // 根据用户id获取对应权限信息
    List<String> selectPermissionsByUserId(@Param("userId") Long userId);

    // 获取所有菜单类型为C或者M的权限Menu
    List<Menu> selectAllRouterMenu();

    // 获取当前用户所具有的权限Menu
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    // 根据角色id查询权限列表
    List<Long> selectMenuListByRoleId(Long roleId);
}

