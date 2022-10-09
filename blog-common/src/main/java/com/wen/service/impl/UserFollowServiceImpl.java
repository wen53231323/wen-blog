package com.wen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.exception.SystemException;
import com.wen.mapper.UserFollowMapper;
import com.wen.pojo.entity.UserFollow;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.service.UserFollowService;
import com.wen.utils.ResponseResult;
import com.wen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * (UserFollow)表服务实现类
 *
 * @author makejava
 * @since 2022-10-07 10:39:18
 */
@Service("userFollowService")
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    public ResponseResult follow(Long userIdB) {
        // （1）获取登录用户ID
        Long userIdA = SecurityUtils.getUserId();

        // （2）如果未登录则抛异常提示登录
        if (Objects.isNull(userIdA)) {
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }

        // （3）查询是否关注，如果为空说明未关注，不为空说明已经关注
        Object count = userFollowMapper.selectFollows(userIdA, userIdB, "1");

        if (count == null) {
            // （4）如果为空说明未关注，开始执行关注，插入一条数据
            userFollowMapper.insertUserFocus(userIdA, userIdB, "1");
            return ResponseResult.okResult();
        } else {
            // （5）如果不为空说明已经关注，执行取消关注，删除指定数据
            userFollowMapper.deleteUserFocus(userIdA, userIdB, "1");
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult isFollow(Long userIdB) {
        // （1）获取登录用户ID
        Long userIdA = SecurityUtils.getUserId();

        // （2）如果未登录则抛异常提示登录
        if (Objects.isNull(userIdA)) {
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }

        // （3）查询是否关注，如果为空说明已经关注
        Object count = userFollowMapper.selectFollows(userIdA, userIdB, "1");

        // （4）如果为空说明未关注，返回未关注
        if (count == null) {
            return ResponseResult.okResult(200, "未关注");
        }

        // （5）执行到这里说明已经关注，判断被关注用户是否也关注了自己，如果关注了自己，则返回已互关
        Object count2 = userFollowMapper.selectFollows( userIdB, userIdA,"1");

        if(count2 == null){
            //（6）未查询到，说明只有自己关注了别人
            return ResponseResult.okResult(200, "已关注");
        } else {
            //（7）查询到，说明对方也关注了自己
            return ResponseResult.okResult(200, "已互关");
        }
    }
}



