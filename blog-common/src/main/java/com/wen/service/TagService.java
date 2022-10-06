package com.wen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.Tag;
import com.wen.utils.ResponseResult;

/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2022-10-06 15:49:24
 */
public interface TagService extends IService<Tag> {

    ResponseResult getListAllTag();
}

