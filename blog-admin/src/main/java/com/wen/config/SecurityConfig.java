package com.wen.config;

import com.wen.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 注入认证异常处理器
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    // 注入授权异常处理器
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    // 注入自定义的认证过滤器
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 注入 认证管理器AuthenticationManager，需要重写authenticationManagerBean()方法
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();//允许跨域
        http.csrf().disable();//关闭csrf
        http.logout().disable();// 关闭默认退出接口

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//不通过Session获取SecurityContext
        http.authorizeRequests()
                .antMatchers("/user/login").anonymous()// 对于登录接口 允许匿名访问（未登录可以访问）
                .antMatchers("/logout").authenticated()// 注销接口需要认证才能访问（登录后可以访问）
                .antMatchers("/comment").authenticated()// 发布评论认证才能访问（登录后可以访问）
                .antMatchers("/user/userInfo").authenticated()// 用户中心认证才能访问（登录后可以访问）
                .antMatchers("/upload").authenticated()// 上传头像才能访问（登录后可以访问）
                .anyRequest().permitAll();// 除了上面的接口，剩下的全部不需要认证即可访问

        // 将自定义的认证过滤器，置于授权过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 配置异常处理器
        http.exceptionHandling()
                // 配置认证异常处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                // 配置授权异常处理器
                .accessDeniedHandler(accessDeniedHandler);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
