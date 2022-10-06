package com.wen.controller;

import com.wen.exception.SystemException;
import com.wen.pojo.dto.LoginUserDTO;
import com.wen.pojo.entity.LoginUser;
import com.wen.pojo.entity.Menu;
import com.wen.pojo.entity.User;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.pojo.vo.AdminUserInfoVo;
import com.wen.pojo.vo.RoutersVo;
import com.wen.pojo.vo.UserInfoVo;
import com.wen.service.AdminLoginService;
import com.wen.service.MenuService;
import com.wen.service.RoleService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import com.wen.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "登录", notes = "登录接口")
    @PostMapping("/login")
    // @RequestBody注解：将请求体信息与控制器方法的形参进行绑定
    public ResponseResult login(@RequestBody LoginUserDTO loginUserDTO) {
        // 统一异常处理
        if (!StringUtils.hasText(loginUserDTO.getAccount())) {
            // 提示必须要传账号
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        ResponseResult login = adminLoginService.login(loginUserDTO);
        return login;
    }

    @ApiOperation(value = "登出", notes = "退出登录接口")
    @PostMapping("/logout")
    public ResponseResult logout() {
        ResponseResult logout = adminLoginService.logout();
        return logout;
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //（1）获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();// 获取登录用户

        //（2）根据用户id查询角色信息
        List<String> roles = roleService.selectRoleByUserId(loginUser.getUser().getId());

        //（3）根据用户id查询权限信息
        List<String> perms = menuService.selectPermissionsByUserId(loginUser.getUser().getId());

        //（4）获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //（5）封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(roles, perms, userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<Menu> getRouters() {
        // 获取用户ID
        Long userId = SecurityUtils.getUserId();

        // 根据用户ID查询出Menu权限，结果是具有子父关系的tree类型
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);

        // 将数据封装
        RoutersVo routersVo = new RoutersVo(menus);

        // 将封装的数据返回
        return ResponseResult.okResult(routersVo);
    }

}
