package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.UserFollow;
import com.wen.utils.ResponseResult;

/**
 * (UserFollow)表服务接口
 *
 * @author makejava
 * @since 2022-10-07 10:39:17
 */
public interface UserFollowService extends IService<UserFollow> {
    // 关注取关接口
    ResponseResult follow(Long followUserId);

    // 查看是否关注接口
    ResponseResult isFollow(Long followUserId);
}

