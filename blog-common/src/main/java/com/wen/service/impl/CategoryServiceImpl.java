package com.wen.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.pojo.constants.SystemConstants;
import com.wen.mapper.ArticleMapper;
import com.wen.mapper.CategoryMapper;
import com.wen.pojo.entity.Article;
import com.wen.pojo.entity.Category;
import com.wen.pojo.vo.CategoryVo;
import com.wen.pojo.vo.PageVo;
import com.wen.service.CategoryService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2022-08-28 12:44:33
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList() {
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
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getAllCategory() {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Category::getStatus, SystemConstants.STATUS_NORMAL); // 分类状态必须为正常的
        lqw.select(Category::getId,Category::getName);// 只查询分类ID与分类名称
        List<Category> categories = categoryMapper.selectList(lqw);
        // -----------------------------使用工具类BeanCopyUtils封装VO-----------------------------
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(category.getName()),Category::getName, category.getName());
        queryWrapper.eq(Objects.nonNull(category.getStatus()),Category::getStatus, category.getStatus());
        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        // 转换成VO
        List<Category> categories = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(categories);
        return pageVo;
    }
}

