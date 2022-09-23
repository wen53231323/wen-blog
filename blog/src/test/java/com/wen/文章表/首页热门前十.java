package com.wen.文章表;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.SpringbootApplication;
import com.wen.controller.ArticleController;
import com.wen.mapper.ArticleMapper;

import com.wen.pojo.entity.Article;
import com.wen.utils.ResponseResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = SpringbootApplication.class)
public class 首页热门前十 {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleController articleController;

    @Test
    public void testService() {
        // 功能等同于QueryWrapper，提供了Lambda表达式的语法可以避免填错列名
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();

        // 必须是正式文章才显示，（草稿不展示，删除的文章不展示）
        lqw.eq(Article::getStatus, 0).eq(Article::getDelFlag, 0);

        // 指定需要查询的字段
        lqw.select(Article::getId,Article::getTitle,Article::getViewCount);

        // 按照浏览量降序排序（由高到低）
        lqw.orderByAsc(Article::getViewCount);

        // 只查询10条数据
        Page<Article> page = new Page<>(1, 10);
        Page<Article> articlePage = articleMapper.selectPage(page, lqw);

        System.out.println(articlePage);
    }

    @Test
    public void testController() {
        ResponseResult responseResult = articleController.hotArticeList();
        System.out.println(responseResult);
    }


}
