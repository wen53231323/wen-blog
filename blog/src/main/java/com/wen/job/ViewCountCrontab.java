package com.wen.job;

import com.wen.mapper.ArticleMapper;
import com.wen.utils.RedisCache;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 定时任务类，每天0,4,8,12,16,20时，把redis中的浏览量，更新到数据库中
 */
@Slf4j
// @Async注解：该类中的所有方法都是异步任务
@Async
@Configuration
// @EnableScheduling注解：开启定时任务
@EnableScheduling
public class ViewCountCrontab {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 每天0,4,8,12,16,20时，把redis中的浏览量，更新到数据库中
     */
    @Scheduled(cron = "0 0 0,4,8,12,16,20 * * ? ")
    public void run() throws InterruptedException {
        log.info("定时任务开始执行...");
        // 从数据库获取redis集合
        Map<String, Integer> cacheMap = redisCache.getCacheMap("Article:viewCount");

        for (Map.Entry<String, Integer> entry : cacheMap.entrySet()) {
            // 更新到数据库
            articleMapper.updateViewCountById(Long.valueOf(entry.getKey()),entry.getValue());
        }
        log.info("定时任务执行结束...");
    }

}
