package com.wen.文章分类表;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.pojo.constants.SystemConstants;
import com.wen.mapper.ArticleMapper;
import com.wen.mapper.CategoryMapper;
import com.wen.pojo.entity.Article;
import com.wen.pojo.entity.Category;
import com.wen.pojo.vo.CategoryVo;
import com.wen.utils.BeanCopyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class 首页分类显示 {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void test() {
        // -----------------------------查询文章表获取文章分类id，并去重-----------------------------
        // QueryWrapper对象：用于封装查询条件的对象，该对象可以动态使用API调用的方法添加条件，最终转化成对应的SQL语句。
        QueryWrapper<Article> qw = new QueryWrapper<>();
        // 必须是正式文章才显示，（草稿不展示，删除的文章不展示）
        qw.lambda().eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 查询文章列表
        List<Article> articles = articleMapper.selectList(qw);
        // 获取文章分类id，并去重
        Set<Long> collect = articles.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        // -----------------------------根据分类id查询文章分类表获取文章分类列表-----------------------------
        // 功能等同于QueryWrapper，提供了Lambda表达式的语法可以避免填错列名
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        // 分类状态必须为正常的
        lqw.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> categories = categoryMapper.selectBatchIds(collect);

        // -----------------------------使用工具类BeanCopyUtils封装VO-----------------------------
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        System.out.println(categoryVos);
    }
}
