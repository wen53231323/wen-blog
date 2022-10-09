package com.wen.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.mapper.RoleMapper;
import com.wen.pojo.dto.EditRoleDTO;
import com.wen.pojo.dto.RoleListDTO;
import com.wen.pojo.entity.Role;
import com.wen.pojo.entity.RoleMenu;
import com.wen.pojo.entity.User;
import com.wen.pojo.vo.PageVo;
import com.wen.service.RoleMenuService;
import com.wen.service.RoleService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

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

    @Override
    public ResponseResult pageRoleList(Integer pageNum, Integer pageSize, RoleListDTO roleListDTO) {
        //分页查询
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(roleListDTO.getRoleName()), Role::getRoleName, roleListDTO.getRoleName());
        lqw.like(StringUtils.hasText(roleListDTO.getStatus()), Role::getStatus, roleListDTO.getStatus());
        Page<Role> page = new Page<>(pageNum, pageSize); // 分页查询指定页面
        // userMapper.selectPage(page, lqw); // 添加查询条件方式一
        page(page, lqw);// 添加查询条件方式二
        List<Role> records = page.getRecords();// 获取全部数据列表
        long total = page.getTotal();// 获取记录的总数
        PageVo pageVo = new PageVo(records, total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult edit(EditRoleDTO editRoleDTO) {
        Role role = BeanCopyUtils.copyBean(editRoleDTO, Role.class);
        roleService.updateById(role);

        return ResponseResult.okResult();
    }
}

