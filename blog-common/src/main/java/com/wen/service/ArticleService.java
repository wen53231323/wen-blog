package com.wen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.pojo.dto.ArticleDTO;
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

    // 添加文章
    ResponseResult addArticle(ArticleDTO articleDTO);

    // 后台-分页查询文章列表，根据标题和摘要模糊查询
    ResponseResult selectArticlePage(Integer pageNum, Integer pageSize, String title, String summary);

    // 后台-根据文章id查询文章详情
    ResponseResult getInfo(Long id);

    // 后台-根据文章ID修改文章
    ResponseResult updateArticleById(ArticleDTO articleDTO);
}
