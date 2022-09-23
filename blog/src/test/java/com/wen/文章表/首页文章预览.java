package com.wen.文章表;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.pojo.constants.SystemConstants;
import com.wen.mapper.ArticleMapper;
import com.wen.mapper.CategoryMapper;
import com.wen.pojo.entity.Article;
import com.wen.pojo.entity.Category;
import com.wen.pojo.vo.ArticleListVo;
import com.wen.pojo.vo.PageVo;
import com.wen.utils.BeanCopyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
public class 首页文章预览 {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void test1() {
        Integer pageNum = 1;
        Integer pageSize = 5;
        Long categoryId = 1L;

        // -----------------------------查询文章表中已发布的文章，并先按照isTop降序然后按照创建时间降序-----------------------------
        // 功能等同于QueryWrapper，提供了Lambda表达式的语法可以避免填错列名
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        // 必须是正式文章才显示，（草稿不展示，删除的文章不展示）
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 如果有 categoryId 就要求查询时要和传入的相同（参数1进行条件判断，如果不为null且大于0，则进行判断）
        lqw.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 首先按照isTop字段降序排序（由高到低）
        lqw.orderByDesc(Article::getIsTop);
        // 然后按照创建时间降序排序（由高到低）
        lqw.orderByDesc(Article::getCreateTime);
        // 分页查询指定页面
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> articlePage = articleMapper.selectPage(page, lqw);// 添加查询条件
        List<Article> records = page.getRecords();// 获取全部数据列表

        // -----------------------------根据分类id查询分类名称-----------------------------
        // 功能等同于QueryWrapper，提供了Lambda表达式的语法可以避免填错列名
        LambdaQueryWrapper<Category> lqw2 = new LambdaQueryWrapper<>();
        // 分类状态必须为正常的
        lqw2.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        lqw2.select(Category::getName);
        for (Article article : records) {
            // 获取分类id
            Long category_id = article.getCategoryId();
            // 如果id存在就根据id查询出分类名称
            Category category = categoryMapper.selectById(category_id);
            // 将分类名称设置到实体类中
            article.setCategoryName(category.getName());
        }

        // -----------------------------使用工具类BeanCopyUtils封装到ArticleListVo-----------------------------
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);

        // -----------------------------将ArticleListVo数据与总记录数封装到分页VO对象-----------------------------
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
    }
    @Test
    public void test2() {
        Integer pageNum = 1;
        Integer pageSize = 5;
        Long categoryId = 1L;

        // -----------------------------查询文章表中已发布的文章，并先按照isTop降序然后按照创建时间降序-----------------------------
        // 功能等同于QueryWrapper，提供了Lambda表达式的语法可以避免填错列名
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        // 必须是正式文章才显示，（草稿不展示，删除的文章不展示）
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 如果有 categoryId 就要求查询时要和传入的相同（参数1进行条件判断，如果不为null且大于0，则进行判断）
        lqw.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 首先按照isTop字段降序排序（由高到低）
        lqw.orderByDesc(Article::getIsTop);
        // 然后按照创建时间降序排序（由高到低）
        lqw.orderByDesc(Article::getCreateTime);
        // 分页查询指定页面
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> articlePage = articleMapper.selectPage(page, lqw);// 添加查询条件
        List<Article> records = page.getRecords();// 获取全部数据列表

        // -----------------------------根据分类id查询分类名称-----------------------------
        // 功能等同于QueryWrapper，提供了Lambda表达式的语法可以避免填错列名
        LambdaQueryWrapper<Category> lqw2 = new LambdaQueryWrapper<>();
        // 分类状态必须为正常的
        lqw2.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        lqw2.select(Category::getName);
        List<Article> collect = records.stream()
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        // 获取分类id
                        Long category_id = article.getCategoryId();
                        // 如果id存在就根据id查询出分类名称
                        Category category = categoryMapper.selectById(category_id);
                        // 将分类名称设置到实体类中
                        article.setCategoryName(category.getName());
                        return article;
                    }
                }).collect(Collectors.toList());

        // -----------------------------使用工具类BeanCopyUtils封装到ArticleListVo-----------------------------
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);

        // -----------------------------将ArticleListVo数据与总记录数封装到分页VO对象-----------------------------
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
    }
}
