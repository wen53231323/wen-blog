package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.Navigation;
import com.wen.utils.ResponseResult;

/**
 * (Navigation)表服务接口
 *
 * @author makejava
 * @since 2022-08-30 12:06:31
 */
public interface NavigationService extends IService<Navigation> {

    // 获取所有导航网站
    ResponseResult getAllNavigation();
}

