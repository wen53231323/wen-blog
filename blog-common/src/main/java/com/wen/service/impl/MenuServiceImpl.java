package com.wen.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.mapper.MenuMapper;
import com.wen.pojo.constants.SystemConstants;
import com.wen.pojo.entity.Article;
import com.wen.pojo.entity.ArticleTag;
import com.wen.pojo.entity.Menu;
import com.wen.pojo.vo.ArticleListVo;
import com.wen.pojo.vo.ArticleVo;
import com.wen.pojo.vo.PageVo;
import com.wen.service.MenuService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import com.wen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-03 23:37:31
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermissionsByUserId(Long userId) {
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
        // （1）如果是管理员,则根据用户id查询权限关键字,返回所有权限
        if (SecurityUtils.isAdmin()) {
            // select * from where menu_type in ("C","F")
            lqw.in(Menu::getMenuType, SystemConstants.MENU_TYPE, SystemConstants.MENU_BUTTON);
            lqw.eq(Menu::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
            List<Menu> menus = menuMapper.selectList(lqw);
            // 获取权限标识并返回
            List<String> perms = new ArrayList<>();
            for (Menu menu : menus) {
                perms.add(menu.getPermissions());
            }
            return perms;
        }

        // （2）否则返回用户具有的权限
        List<String> list = menuMapper.selectPermissionsByUserId(userId);

        return list;
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus = new ArrayList<>();

        // 判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            // 如果是，获取所有菜单类型为C或者M的权限Menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 如果不是，获取当前用户所具有的权限Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建具有子父关系的tree类型
        List<Menu> menuTree = builderMenuTree(menus, 0L);
        return menuTree;
    }

    /**
     * 先找出第一层的菜单，然后去找他们的子菜单设置到children属性中
     */
    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))// 调用getChildren()获取子元素
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))// 调用自己getChildren()设置子元素的子元素
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public List<Menu> selectMenuList(String menuName, String status) {
        // （1）查询文章表中已发布的文章
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);// 根据文章标题模糊查询
        lqw.like(StringUtils.hasText(status), Menu::getStatus, status);// 根据文章摘要模糊查询
        lqw.orderByAsc(Menu::getParentId, Menu::getOrderNum);// 根据parent_id和order_num排序（由低到高）
        List<Menu> menus = menuMapper.selectList(lqw);// 获取全部数据列表

        //（2）使用工具类BeanCopyUtils封装到ArticleListVo

        return menus;
    }

    @Override
    public ResponseResult getInfo(Long id) {
        Menu menu = menuMapper.selectById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        menuMapper.updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        List<Long> longs = getBaseMapper().selectMenuListByRoleId(roleId);
        return longs;
    }
}

