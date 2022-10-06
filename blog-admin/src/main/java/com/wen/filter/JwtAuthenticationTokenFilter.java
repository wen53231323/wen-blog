package com.wen.filter;

import com.alibaba.fastjson.JSON;
import com.wen.pojo.entity.LoginUser;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.utils.JwtUtil;
import com.wen.utils.RedisCache;
import com.wen.utils.ResponseResult;
import com.wen.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * 认证过滤器
 * 验证token，判断是否已登录，
 * 如果已经登录则解析token获取用户id，根据用户id从redis获取用户信息存入SecurityContextHolder，
 * 如果没有登录，执行放行，交给后续的认证过滤器判断
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO 从请求头中获取token
        String token = request.getHeader("token");

        //  TODO 如果token没有内容就执行放行，后续的认证过滤器会判断
        if (!StringUtils.hasText(token)) {
            // 说明该接口不需要登录  直接放行
            // 直接放行相当于不设置SecurityContextHolder，不设置就没法通过认证，会被后面的filter给拦住
            filterChain.doFilter(request, response);
            return;
        }

        // TODO 使用jwt工具类解析token获取用户id
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常的两种情况：token超时、token非法
            // 响应403告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();

        // TODO 根据用户id从Redis获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("adminLogin:" + userId);
        // 如果没获取到用户，说明用户过期或未登录
        if (Objects.isNull(loginUser)) {
            // 说明登录过期，提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        // TODO 存入SecurityContextHolder
        // 用户在完成登录后 Security 会将用户信息存储到SecurityContextHolder类中，之后其他流程需要得到用户信息时都是从这个类中获得
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 执行放行
        filterChain.doFilter(request, response);
    }


}
