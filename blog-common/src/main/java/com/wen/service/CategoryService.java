package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.Category;
import com.wen.pojo.vo.PageVo;
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

    // 获取所有分类
    ResponseResult getAllCategory();

    // 获取分类分页
    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}

