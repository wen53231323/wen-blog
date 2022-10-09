package com.wen.service;

public interface PermissionService {
    // 判断当前用户是否具有权限permission
    boolean hasPermission(String permission);
}
