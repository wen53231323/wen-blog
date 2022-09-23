package com.wen.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.wen.utils.FastJson2JsonRedisSerializer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
// redis的固定模板，在真实开发中直接使用即可
// @Configuration注解：将类标识为配置类
@Configuration
@EnableCaching
@SpringBootConfiguration
public class RedisConfig extends CachingConfigurerSupport {
    /***
     * 配置自定义的Redis模板，可以使对象序列化存入redis，从而解决乱码
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        //连接redis模板
        RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(factory);
        //加入自己的序列化方式
        FastJson2JsonRedisSerializer fastJsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);
        //redis开启事务
        template.setEnableTransactionSupport(true);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}