package com.wen.controller;

import com.wen.pojo.dto.ArticleDTO;
import com.wen.pojo.entity.LoginUser;
import com.wen.pojo.entity.Menu;
import com.wen.pojo.entity.User;
import com.wen.pojo.vo.*;
import com.wen.service.MenuService;
import com.wen.service.RoleService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import com.wen.utils.SecurityUtils;
import com.wen.utils.SystemConverter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单列表，根据菜单名称和菜单名称模糊查询
     *
     * @param menuName 菜单名称
     * @param status   菜单名称
     */
    @ApiOperation(value = "查询菜单列表", notes = "查询菜单列表，根据菜单名称和菜单名称模糊查询")
    @GetMapping("/menu/list")
    public ResponseResult getMenuList(String menuName, String status) {
        List<Menu> menus = menuService.selectMenuList(menuName, status);
        return ResponseResult.okResult(menus);
    }

    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    @PostMapping("/menu")
    public ResponseResult addMenu(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @DeleteMapping("/menu/{id}")
    public ResponseResult deleteMenu(@PathVariable("id") Long id) {
        menuService.removeById(id); // 删除菜单
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "菜单详情", notes = "根据文章id查询文章详情")
    @GetMapping("/menu/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        ResponseResult result = menuService.getInfo(id);
        return result;
    }

    @ApiOperation(value = "修改文章", notes = "修改文章")
    @PutMapping("/menu")
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        ResponseResult result = menuService.updateMenu(menu);
        return result;
    }

    @ApiOperation(value = "菜单树", notes = "获取菜单下拉树列表")
    @GetMapping("/menu/treeselect")
    public ResponseResult getTreeselect() {
        // 查询菜单列表
        List<Menu> menus = menuService.selectMenuList(null, null);
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/menu/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(null, null);
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }

}
