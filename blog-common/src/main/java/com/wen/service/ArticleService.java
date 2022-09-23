package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.entity.Article;
import com.wen.utils.ResponseResult;

public interface ArticleService extends IService<Article> {
    // 获取文章列表
    ResponseResult articeList(Integer pageNum, Integer pageSize, Long categoryId);

    // 降序查询浏览量前10的文章
    ResponseResult hotArticeList();

    // 根据文章id查询文章详情
    ResponseResult getArticeDetail(Long id);

    // 更新浏览量时，更新redis中的数据
    ResponseResult updateViewCount(Long id);
}
