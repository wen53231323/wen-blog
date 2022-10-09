package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.pojo.constants.SystemConstants;
import com.wen.mapper.ArticleMapper;
import com.wen.mapper.CategoryMapper;
import com.wen.pojo.dto.ArticleDTO;
import com.wen.pojo.entity.Article;
import com.wen.pojo.entity.ArticleTag;
import com.wen.pojo.entity.Category;
import com.wen.pojo.vo.*;
import com.wen.service.ArticleService;
import com.wen.service.ArticleTagService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.RedisCache;
import com.wen.utils.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 首页文章预览，若传入分类id则查询该分类下的文章
     * （1）分页查询文章表中已发布的文章，先按isTop降序，后按创建时间降序
     * （2）根据分类id查询分类名称，设置到实体类中
     * （3）使用工具类BeanCopyUtils封装到ArticleListVo返回前端
     */
    @Override
    public ResponseResult articeList(Integer pageNum, Integer pageSize, Long categoryId) {
        // （1）查询文章表中已发布的文章，并先按照isTop降序然后按照创建时间降序-----------------------------
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);// 必须是正式文章才显示，（草稿不展示，删除的文章不展示）
        lqw.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);// 分类id
        lqw.orderByDesc(Article::getIsTop);// 首先按照isTop字段降序排序（由高到低）
        lqw.orderByDesc(Article::getCreateTime);// 然后按照创建时间降序排序（由高到低）
        Page<Article> page = new Page<>(pageNum, pageSize);// 分页查询指定页面
        articleMapper.selectPage(page, lqw); // 添加查询条件方式一
        // page(page, lqw); // 添加查询条件方式二
        List<Article> records = page.getRecords();// 获取全部数据列表

        //（2）根据分类id查询分类名称-----------------------------
        LambdaQueryWrapper<Category> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);// 分类状态必须为正常的
        lqw2.select(Category::getName);
        for (Article article : records) {
            Long category_id = article.getCategoryId();// 获取分类id
            Category category = categoryMapper.selectById(category_id);// 如果id存在就根据id查询出分类名称
            article.setCategoryName(category.getName());// 将分类名称设置到实体类中
        }

        //（3）使用工具类BeanCopyUtils封装到ArticleListVo-----------------------------
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);

        //（4）将ArticleListVo数据与总记录数封装到分页VO对象-----------------------------
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 查询浏览量最高的前10篇文章的信息，按浏览量降序排列（由高到低），展示文章标题和浏览量，点击标题后进入文章详情
     */
    @Override
    public ResponseResult hotArticeList() {
        // -----------------------------查询文章表中已发布的10篇文章，并按照浏览量降序-----------------------------
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);// 必须是正式文章才显示，（草稿不展示，删除的文章不展示）
        lqw.orderByAsc(Article::getViewCount);// 按照浏览量降序排序（由高到低）
        Page<Article> page = new Page<>(1, 10);// 只查询10条数据
        articleMapper.selectPage(page, lqw); // 添加查询条件
        List<Article> records = page.getRecords();// 获取全部数据列表

        // -----------------------------VO优化方式一：循环加入vo对象-----------------------------
        List<HotArticleVo> hotArticleVos1 = new ArrayList<>();
        for (Article article : records) {
            HotArticleVo vo = new HotArticleVo();
            // 使用Spring的工具类实现bean拷贝
            BeanUtils.copyProperties(article, vo);
            hotArticleVos1.add(vo);
        }
        // -----------------------------VO优化方式二：使用工具类BeanCopyUtils-----------------------------
        List<HotArticleVo> hotArticleVos2 = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos2);
    }

    // 根据文章id查询文章详情
    @Override
    public ResponseResult getArticeDetail(Long id) {
        Article article = articleMapper.selectById(id);
        Long category_id = article.getCategoryId();// 获取分类id

        // -----------------------------根据分类id查询分类名称-----------------------------
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);// 分类状态必须为正常的
        lqw.select(Category::getName);
        // 如果id存在就根据id查询出分类名称
        if (Objects.nonNull(category_id) && category_id > 0) {
            Category category = categoryMapper.selectById(category_id);
            article.setCategoryName(category.getName());// 将分类名称设置到实体类中
        }

        // 将redis中的浏览量添加
        Integer viewCount = redisCache.getCacheMapValue("Article:viewCount", id.toString());
        article.setViewCount(viewCount);

        // -----------------------------使用工具类BeanCopyUtils封装到VO-----------------------------
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新浏览量加一
        redisCache.incrementCacheMapValue("Article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional // @Transactional：该类中的方法会被事务管理
    public ResponseResult addArticle(@RequestBody ArticleDTO articleDTO) {
        // 添加到博客表
        Article article = BeanCopyUtils.copyBean(articleDTO, Article.class);
        save(article);

        // 添加 博客与标签的关联关系
        List<ArticleTag> articleTags = articleDTO.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    /**
     * 后台接口：分页查询文章列表，根据标题和摘要模糊查询
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param title    文章标题
     * @param summary  文章摘要
     */
    @Override
    public ResponseResult selectArticlePage(Integer pageNum, Integer pageSize, String title, String summary) {
        // （1）查询文章表中已发布的文章
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);// 必须是正式文章才显示，（草稿不展示，删除的文章不展示）
        lqw.orderByDesc(Article::getCreateTime);// 然后按照创建时间降序排序（由高到低）
        lqw.like(StringUtils.hasText(title), Article::getTitle, title);// 根据文章标题模糊查询
        lqw.like(StringUtils.hasText(summary), Article::getSummary, summary);// 根据文章摘要模糊查询
        Page<Article> page = new Page<>(pageNum, pageSize); // 分页查询指定页面
        // articleMapper.selectPage(page, lqw); // 添加查询条件方式一
        page(page, lqw); // 添加查询条件方式二
        List<Article> articles = page.getRecords();// 获取全部数据列表
        long total = page.getTotal();// 获取记录的总数

        //（2）使用工具类BeanCopyUtils封装到ArticleListVo-----------------------------
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        //（3）将数据与总记录数封装到分页VO对象
        PageVo pageVo = new PageVo(articleListVos, total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getInfo(Long id) {
        Article article = articleMapper.selectById(id);
        LambdaQueryWrapper<ArticleTag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> articleTags = articleTagService.list(lqw); //获取关联标签

        List<Long> tags = articleTags.stream()
                .map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());

        ArticleVo articleVo = BeanCopyUtils.copyBean(article,ArticleVo.class);// 拷贝到ArticleVo
        articleVo.setTags(tags);
        return ResponseResult.okResult(articleVo);
    }

    @Override
    public ResponseResult updateArticleById(ArticleDTO articleDTO) {
        //（1）更新博客信息
        Article article = BeanCopyUtils.copyBean(articleDTO, Article.class);
        articleMapper.updateById(article);

        //（2）删除原有的 标签和博客的关联
        LambdaQueryWrapper<ArticleTag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(lqw);

        //（3）添加新的博客和标签的关联信息
        List<ArticleTag> articleTags = articleDTO.getTags().stream()
                .map(tagId -> new ArticleTag(articleDTO.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

}
