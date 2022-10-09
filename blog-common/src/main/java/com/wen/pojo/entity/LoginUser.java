package com.wen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class LoginUser implements UserDetails {
    // 用户信息
    private User user;

    // 权限列表
    private List<String> permissions;

    // 返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 获取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 获取用户账号
    @Override
    public String getUsername() {
        return user.getAccount();
    }

    // 判断是否没过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否没被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 账户密码是否没有过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
