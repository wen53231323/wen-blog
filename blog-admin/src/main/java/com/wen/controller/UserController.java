package com.wen.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wen.exception.SystemException;
import com.wen.pojo.dto.*;
import com.wen.pojo.entity.Role;
import com.wen.pojo.entity.Tag;
import com.wen.pojo.entity.User;
import com.wen.pojo.entity.UserRole;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.service.UserRoleService;
import com.wen.service.UserService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "导出Excel", notes = "导出用户信息到Excel")
    @GetMapping("/user/export")
    public void excelExport(HttpServletResponse response) {

    }

    @ApiOperation(value = "用户列表", notes = "用户列表")
    @GetMapping("/user/list")
    public ResponseResult getUserPage(Integer pageNum, Integer pageSize, UserListDTO userListDTO) {
        ResponseResult categoryList = userService.pageUserList(pageNum, pageSize, userListDTO);
        return categoryList;
    }


    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping("/user")
    public ResponseResult add(@RequestBody AddUserDTO addUserDTO) {
        ResponseResult result = userService.addUser(addUserDTO);
        return result;
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping("/user/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        userService.removeById(id);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PutMapping("/user")
    public ResponseResult edit(@RequestBody EditUserDTO editUserDTO) {
        User user = BeanCopyUtils.copyBean(editUserDTO, User.class);
        userService.updateById(user);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "用户详情", notes = "用户详情")
    @GetMapping("/user/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return ResponseResult.okResult(user);
    }

    @ApiOperation(value = "用户状态修改", notes = "用户状态修改")
    @PutMapping("/user/changeStatus")
    public ResponseResult changeStatus(@RequestBody User user) {
        userService.updateById(user);
        return ResponseResult.okResult();
    }
}
