package com.wen.service.impl;

import com.wen.pojo.dto.LoginUserDTO;
import com.wen.pojo.entity.LoginUser;
import com.wen.service.AdminLoginService;
import com.wen.utils.JwtUtil;
import com.wen.utils.RedisCache;
import com.wen.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * （1）检查参数是否合法
     * （2）如果合法，就封装为UsernamePasswordAuthenticationToken，提供给认证管理器进行认证
     * （3）如果认证失败，登录失败
     * （4）如果认证成功
     * 使用用户id作为key，把完整的用户信息转为存入redis
     * 使用用户id生成一个JWT，把Token和用户信息响应给前端
     */
    @Override
    public ResponseResult login(LoginUserDTO loginUserDTO) {
        // （1）获取参数进行判断
        String userName = loginUserDTO.getAccount();
        String password = loginUserDTO.getPassword();
        if (!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
            throw new RuntimeException("用户名或密码未填写");
        }

        // （2）如果合法，就封装为UsernamePasswordAuthenticationToken，提供给认证管理器进行认证
        // 将用户名和密码封装为UsernamePasswordAuthenticationToken，并提供给认证管理器进行认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        // 通过认证管理器AuthenticationManager中的authenticate方法来进行用户认证
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //（3）如果认证失败，登录失败
        // 如果返回值authenticate为null，表示认证没通过，给出对应的错误提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        // （4）反之表示认证通过，使用用户id作为key，把完整的用户信息转为存入redis
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        // 将用户id作为key，把完整的用户信息转为存入redis
        redisCache.setCacheObject("adminLogin:" + userId, loginUser);

        // （5）使用用户id生成一个JWT，把Token和用户信息响应给前端
        // 使用用户id生成一个JWT，把Token和用户信息响应给前端
        String jwt = JwtUtil.createJWT(userId);

        // （6）封装token并返回
        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", jwt);
        return ResponseResult.okResult(tokenMap);
    }

    @Override
    public ResponseResult logout() {
        // TODO 获取用户id
        // 用户在完成登录后 Security 会将用户信息存储到SecurityContextHolder类中，所以可以从中获取用户信息

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        // TODO 根据用户id将Redis中的信息删除
        redisCache.deleteObject("adminLogin:" + userid);

        return ResponseResult.okResult();
    }
}
