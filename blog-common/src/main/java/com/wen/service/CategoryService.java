package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.Category;
import com.wen.utils.ResponseResult;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2022-08-28 12:44:33
 */
public interface CategoryService extends IService<Category> {
    // 获取文章分类列表
    ResponseResult getCategoryList();
}

