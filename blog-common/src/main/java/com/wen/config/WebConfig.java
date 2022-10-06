package com.wen.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

// ------------------------------Springboot配置接口WebMvcConfigurer------------------------------
// @Configuration注解：将类标识为配置类
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 此种设置跨域的方式，在自定义拦截器的情况下可能导致跨域失效
     * 原因：当跨越请求在跨域请求拦截器之前的拦截器处理时就异常返回了，那么响应的response报文头部关于跨域允许的信息就没有被正确设置，导致浏览器认为服务不允许跨域，而造成错误。
     * 解决：自定义跨域过滤器解决跨域问题（该过滤器最好放在其他过滤器之前）
     *
     * @param registry
     */
    // 重写父类提供的跨域请求处理的接口
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }

    // ------------------------------使用FsatJson配置日期格式------------------------------
    // 配置fastjson解析器的规则
    // 使用@Bean注入fastJsonHttpMessageConvert
    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        // 定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 添加fastjson的配置信息，比如是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);//是否输出值为null的字段，默认为false
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty);//将Collection类型字段的字段空值输出为[]
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);//将字符串类型字段的空值输出为空字符串
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect); // 禁用循环引用
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);// 结果是否格式化，默认为false
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");// 配置全局日期格式
        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);


        fastConverter.setFastJsonConfig(fastJsonConfig);// 在convert中添加配置信息
        fastConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));//设置支持的媒体类型
        fastConverter.setDefaultCharset(StandardCharsets.UTF_8);//设置默认字符集
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }
    // 集成WebMvcConfigurationSupport，并重写configureMessageConverters方法，加入fastjson的配置规则
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }



}