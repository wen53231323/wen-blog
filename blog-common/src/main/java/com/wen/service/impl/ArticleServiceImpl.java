package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.pojo.constants.SystemConstants;
import com.wen.mapper.ArticleMapper;
import com.wen.mapper.CategoryMapper;
import com.wen.pojo.entity.Article;
import com.wen.pojo.entity.Category;
import com.wen.pojo.vo.ArticleDetailVo;
import com.wen.pojo.vo.ArticleListVo;
import com.wen.pojo.vo.HotArticleVo;
import com.wen.pojo.vo.PageVo;
import com.wen.service.ArticleService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.RedisCache;
import com.wen.utils.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 首页文章预览，若传入分类id则查询该分类下的文章
     * （1）分页查询文章表中已发布的文章，先按isTop降序，后按创建时间降序
     * （2）根据分类id查询分类名称，设置到实体类中
     * （3）使用工具类BeanCopyUtils封装到ArticleListVo返回前端
     */
    @Override
    public ResponseResult articeList(Integer pageNum, Integer pageSize, Long categoryId) {
        // -----------------------------查询文章表中已发布的文章，并先按照isTop降序然后按照创建时间降序-----------------------------
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);// 必须是正式文章才显示，（草稿不展示，删除的文章不展示）
        lqw.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);// 分类id
        lqw.orderByDesc(Article::getIsTop);// 首先按照isTop字段降序排序（由高到低）
        lqw.orderByDesc(Article::getCreateTime);// 然后按照创建时间降序排序（由高到低）
        Page<Article> page = new Page<>(pageNum, pageSize);// 分页查询指定页面
        articleMapper.selectPage(page, lqw); // 添加查询条件
        List<Article> records = page.getRecords();// 获取全部数据列表

        // -----------------------------根据分类id查询分类名称-----------------------------
        LambdaQueryWrapper<Category> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);// 分类状态必须为正常的
        lqw2.select(Category::getName);
        for (Article article : records) {
            Long category_id = article.getCategoryId();// 获取分类id
            Category category = categoryMapper.selectById(category_id);// 如果id存在就根据id查询出分类名称
            article.setCategoryName(category.getName());// 将分类名称设置到实体类中
        }

        // -----------------------------使用工具类BeanCopyUtils封装到ArticleListVo-----------------------------
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);

        // -----------------------------将ArticleListVo数据与总记录数封装到分页VO对象-----------------------------
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

}
