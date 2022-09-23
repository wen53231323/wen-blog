package com.wen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 多线程执行定时任务的配置
 * */
// @EnableAsync注解：开启异步事件的支持
@EnableAsync
@Configuration
public class AsyncConfig {
    // 此处成员变量建议从配置文件中读取
    private int corePoolSize = 10;
    private int maxPoolSize = 200;
    private int queueCapacity = 100;
    private int keepAliveSeconds = 300;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize); //核心线程数
        executor.setMaxPoolSize(maxPoolSize);  //最大线程数
        executor.setQueueCapacity(queueCapacity); //队列大小
        executor.setKeepAliveSeconds(keepAliveSeconds); //线程最大空闲时间
        executor.setThreadNamePrefix("async-Executor-"); //指定用于新创建的线程名称的前缀。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略（一共四种）
        // 这一步千万不能忘了，否则报错： java.lang.IllegalStateException: ThreadPoolTaskExecutor not initialized
        executor.initialize();
        return executor;
    }

}
