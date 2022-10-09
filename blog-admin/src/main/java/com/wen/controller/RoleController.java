package com.wen.controller;

import com.wen.pojo.dto.*;
import com.wen.pojo.entity.Role;
import com.wen.pojo.entity.RoleMenu;
import com.wen.pojo.entity.UserRole;
import com.wen.service.RoleMenuService;
import com.wen.service.RoleService;
import com.wen.service.UserRoleService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/system")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @ApiOperation(value = "角色列表", notes = "角色列表")
    @GetMapping("/role/list")
    public ResponseResult getRolePage(Integer pageNum, Integer pageSize, RoleListDTO roleListDTO) {
        ResponseResult categoryList = roleService.pageRoleList(pageNum, pageSize, roleListDTO);
        return categoryList;
    }

    @ApiOperation(value = "查询所有角色", notes = "查询所有角色")
    @GetMapping("/role/listAllRole")
    public ResponseResult getListAllRole() {
        List<Role> list = roleService.list();
        return ResponseResult.okResult(list);
    }

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping("/role")
    public ResponseResult add(@RequestBody AddRoleDTO addRoleDTO) {
        Role role = BeanCopyUtils.copyBean(addRoleDTO, Role.class);
        roleService.save(role);
        // 如果角色存在权限，则添加角色到 角色权限关联表
        if (addRoleDTO.getMenuIds() != null && addRoleDTO.getMenuIds().length > 0) {
            List<RoleMenu> collect = Arrays.stream(addRoleDTO.getMenuIds())
                    .map(menuId -> new RoleMenu(role.getId(), menuId))
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(collect);
        }

        return ResponseResult.okResult();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @DeleteMapping("/role/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PutMapping("/role")
    public ResponseResult edit(@RequestBody EditRoleDTO editRoleDTO) {

        ResponseResult result = roleService.edit(editRoleDTO);

        return result;
    }

    @ApiOperation(value = "角色详情", notes = "角色详情")
    @GetMapping("/role/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        Role role = roleService.getById(id);
        return ResponseResult.okResult(role);
    }

    @ApiOperation(value = "角色状态修改", notes = "角色状态修改")
    @PutMapping("/role/changeStatus")
    public ResponseResult changeStatus(@RequestBody Role role) {
        roleService.updateById(role);
        return ResponseResult.okResult();
    }

}
