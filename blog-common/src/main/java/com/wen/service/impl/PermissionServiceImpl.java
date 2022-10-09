package com.wen.service.impl;


import com.wen.service.PermissionService;
import com.wen.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionServiceImpl implements PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return true有、false无
     */
    public boolean hasPermission(String permission){
        // 如果是超级管理员  直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }else {
            // 否则 获取当前登录用户所具有的权限列表 如何判断是否存在permission
            List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
            return permissions.contains(permission);
        }
    }
}
