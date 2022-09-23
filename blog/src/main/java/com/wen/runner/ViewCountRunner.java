package com.wen.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.mapper.ArticleMapper;
import com.wen.pojo.entity.Article;
import com.wen.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 预加载类，容器启动成功后自动执行
 * 在应用启动时，把博客的浏览量存储到redis中（预加载）
 * */
@Slf4j
@Component
// @Order注解：对预加载类执行顺序排序
@Order(value = 1)
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;
    /**
     * （1）查询数据库中文章表，获取文章id与文章浏览量viewCount
     * （2）将文章id与文章浏览量viewCount添加到map集合，
     * （3）以"Article:viewCount"为key，map集合为value存储到redis中
     * */
    @Override
    public void run(String... args) throws Exception {
        log.info("数据初始化开始...");
        // 查询数据库
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.select(Article::getId,Article::getViewCount);
        List<Article> articles = articleMapper.selectList(lqw);

        // 将文章id与文章浏览量viewCount添加到map集合
        HashMap<String, Integer> viewCountMap = new HashMap<>();
        for (Article article : articles) {
            viewCountMap.put(article.getId().toString(),article.getViewCount());
        }

        // 存入redis
        redisCache.setCacheMap("Article:viewCount",viewCountMap);
        log.info("数据已写入redis...");
    }

}
